package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Student;
import com.diplom.electronicrecord.service.StudentService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import com.diplom.electronicrecord.util.ValidationUtil;
import com.diplom.electronicrecord.view.admin.FxmlViewGroup;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


import javax.validation.ValidationException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;


@Component
@Primary
public class StudentController implements Initializable {

    protected ObservableList<Student> students = FXCollections.observableArrayList();

    @FXML
    protected StackPane rootPane;

    @FXML
    protected AnchorPane contentPane;

    @FXML
    protected TableView<Student> tableView;

    @FXML
    protected TableColumn<Student, Integer> numberStudent;

    @FXML
    protected TableColumn<Student, String> fullName;

    @FXML
    protected TableColumn<Student, Integer> numberRecord;

    @FXML
    protected TableColumn<Student, String> login;

    @FXML
    protected TableColumn<Student, Integer> id;

    @FXML
    private TableColumn<Student, String> password;

    @FXML
    private MenuItem con_edit;

    @FXML
    private MenuItem con_addStudent;

    @FXML
    private MenuItem con_delete;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private FontAwesomeIconView iconStatement;

    @FXML
    private JFXButton btn_addStudent;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private JFXButton bnt_NullGroup;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_autoGenerateLogin;

    @FXML
    private FontAwesomeIconView commit;

    @FXML
    private FontAwesomeIconView rollback;


    protected StudentService studentService;

    private FxmlViewGroup createStud;

    private FxmlViewGroup noGroup;

    private SpringFXMLLoader fxmlLoader;

    private CreateStudentController createStudController;

    private StudentIsNullGroupController studentIsNullGroupController;

    private Group group;

    private Boolean isReady = Boolean.FALSE;


    @Autowired
    public StudentController(@Qualifier("CreateStudent") FxmlViewGroup createStud,
                             SpringFXMLLoader fxmlLoader,
                             CreateStudentController createStudController,
                             @Qualifier("NoGroup") FxmlViewGroup noGroup, StudentIsNullGroupController studentIsNullGroupController) {
        this.createStud = createStud;
        this.fxmlLoader = fxmlLoader;
        this.createStudController = createStudController;
        this.noGroup = noGroup;
        this.studentIsNullGroupController = studentIsNullGroupController;
    }

    public StudentController() {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadStudentInTable();
        changeTextField();
    }


    protected void initCol() {
        numberStudent.setCellValueFactory(new PropertyValueFactory<>("numberStudent"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        numberRecord.setCellValueFactory(new PropertyValueFactory<>("numberBook"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));

    }

    protected void loadStudentInTable() {
        students.clear();
        int number = 1;
        for (Student student : studentService.findAllStudentByGroupId(group.getId())) {
            student.setNumberStudent(number);
            student.setFullName(student.getSurname() + " " + student.getName() + " " + student.getPatronymic());
            students.add(student);
            number++;
        }
        tableView.setItems(students);
    }

    @FXML
    protected void exportCSV(ActionEvent event) {
        try {
            String path = AlertMaker.initFileChooserExport("CSV (разделители - точка с запятой) (*.csv)", "*.csv");
            if (!path.isEmpty()) {

                HandlerCSVUtil.exportFile(getValuesCSV(), getHeaderCSV(), ';', path);
            }
        } catch (IOException e) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Ошибка при сохранение!", "Что-то пошло не так...");
        }


    }

    @FXML
    void handleCommit(MouseEvent event) {
        if (isReady) {
            saveAllStudent();
        }
    }

    private void saveAllStudent() {

        if (checkIsEmptyField(students)) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, "Обнаружены пустые поля",
                    "Нажмите Автогенерация лоигна и повторите заного");
            return;
        }

        Student currentStudent = null;
        List<Student> studentsForSave = new ArrayList<>();
        try {
            for (Student student : students) {

                if (student.getId() == 0) {
                    currentStudent = student;
                    studentService.checkExistStudent
                            (currentStudent.getLogin(), currentStudent.getPassword());
                    studentsForSave.add(currentStudent);
                }
            }
            studentService.saveAll(studentsForSave);
            resetColor();
            isReady = Boolean.FALSE;
            cleanDisable();
        } catch (AlreadyExistException e) {
            handleRefresh(new ActionEvent());
            AlertMaker.showErrorMessage("Пользователь " + currentStudent.getFullName() + " уже существует",
                    "Дубликатов в базе данных хранится не может!");
        } catch (ValidationException e) {
            AlertMaker.showErrorMessage("Ошибка при сохранение", "Данные не прошли проверку");
        }
    }

    private boolean checkIsEmptyField(List<Student> students) {
        for (Student student : students) {
            if (ValidationUtil.isEmpty(student.getLogin())) {
                return true;
            }
            if (ValidationUtil.isEmpty(student.getPassword())) {
                return true;
            }
        }
        return false;
    }


    @FXML
    void handleGenerateLogin(ActionEvent event) {

        int amount = 0;

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (ValidationUtil.isEmpty(student.getLogin())) {
                student.setLogin(generateLogin(student));
                students.set(i, student);
                amount++;
            }
            if (ValidationUtil.isEmpty(student.getPassword())) {
                student.setPassword(generatePassword());
                students.set(i, student);
                amount++;
            }
        }
        if (amount == 0) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Логин(-ы) и парол(-ь/-и) не были сгенерированы", "Нет пустых полей для автогенерации");
        } else {
            isReady = Boolean.TRUE;
            setColor();
        }

    }

    @FXML
    void handleImportCSV(ActionEvent event) {
        String path = AlertMaker.initFileChooserImport
                ("CSV (разделители - точка с запятой) (*.csv)", "*.csv");
        if (!path.isEmpty()) {
            readerCSV(path);
        }
    }

    @FXML
    void handleIsNullGroup(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        studentIsNullGroupController.setGroup(group);
        StageManager stageManager = new StageManager(fxmlLoader, stage);
        stageManager.switchScene(noGroup);
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }

    @FXML
    void handleMouseClicked(MouseEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            setColorBorderButton();
        }
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        cleanDisable();
        resetColor();
        isReady = Boolean.FALSE;
        btn_edit.setStyle("-fx-border-color:white");
        btn_delete.setStyle("-fx-border-color:white");
        loadStudentInTable();
    }


    @FXML
    void handleRollback(MouseEvent event) {
        if (isReady) {
            handleRefresh(new ActionEvent());
        }
    }

    @FXML
    void handleStudentAddOption(ActionEvent event) {
        createStudController.setGroup(group);
        initUI();
    }

    @FXML
    void handleStudentDeleteOption(ActionEvent event) {
        Student student = tableView.getSelectionModel().getSelectedItem();
        if (student == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Студент не выбран!", "Пожалуйста, выберите Студента");
            return;
        }
        Alert alert = new Alert(CONFIRMATION);
        alert.setTitle("Удаление студента ");
        alert.setHeaderText("Удалить cтудента " + student.getFullName() + " ?");
        alert.setContentText("Вы действительно хотите удалить студента?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {

            studentService.delete(student);
            handleRefresh(new ActionEvent());

        }

    }

    @FXML
    void handleStudentEditOption(ActionEvent event) {
        Student student = tableView.getSelectionModel().getSelectedItem();
        if (student == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Студент не выбран!", "Пожалуйста, выберите Студента");
            return;
        }
        createStudController.setStudentForUpdate(student);
        initUI();
    }

    private void initUI() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        StageManager stageManager = new StageManager(fxmlLoader, stage);
        stageManager.switchScene(createStud);
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
            createStudController.setInEditMode(false);
        });
    }

    protected void changeTextField() {
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            students.clear();
            int number = 1;
            for (Student student : studentService.findAllStudentByGroupIdAndFullName(group.getId(), newValue)) {
                student.setNumberStudent(number);
                student.setFullName(student.getSurname() + " " + student.getName() + " " + student.getPatronymic());
                students.add(student);
            }
        });
    }

    private void readerCSV(String patch) {
        try {
            students.addAll(studentService.readerCSV(patch, group, students.size()));
            setDisable();
            isReady = true;
            setColor();
        } catch (IOException e) {
            cleanDisable();
            AlertMaker.showErrorMessage("Ошибка при чтении файла", "Возможно файл поврежден или не тот формат");
        } catch (Exception e) {
            AlertMaker.showErrorMessage("Ошибка при чтении файла", "Возможно несовподение типов или пустой");
            cleanDisable();
        }

    }

    private void setColorBorderButton() {
        btn_edit.setStyle("-fx-border-color:#70ff7e");
        btn_delete.setStyle("-fx-border-color:#70ff7e");
    }

    private List<String> getHeaderCSV() {
        List<String> strings = new ArrayList<>();
        strings.add("№");
        strings.add("ФИО");
        strings.add("Номер зачетки");
        strings.add("Логин");
        strings.add("Пароль");
        return strings;
    }

    private List<String[]> getValuesCSV() {
        List<String[]> strings = new ArrayList<>();
        for (Student student : students) {
            String[] line = new String[5];
            line[0] = String.valueOf(student.getNumberStudent());
            line[1] = student.getSurname() + " " + student.getName() + " " + student.getPatronymic();
            line[2] = String.valueOf(student.getNumberBook());
            line[3] = student.getLogin();
            line[4] = student.getPassword();
            strings.add(line);
        }
        return strings;
    }


    protected void setGroup(Group group) {
        this.group = group;
    }

    protected Group getGroup() {
        return group;
    }

    private String generateLogin(Student student) {
        try {
            char[] name = student.getName().toCharArray();
            char[] surname = student.getSurname().toCharArray();
            char[] patronymic = student.getPatronymic().toCharArray();
            String generateString = "" + name[0] + name[1] + surname[0] + patronymic[0];
            return generateString.toUpperCase();
        } catch (Exception e) {
            AlertMaker.showErrorMessage("Произошла ошибка", "Проверьте фалй на наличие проебелов в ФИО");
        }
        handleRefresh(new ActionEvent());
        return "";

    }

    private String generatePassword() {
        return new Random().ints(7, 48, 58).mapToObj
                (i -> String.valueOf((char) i)).collect(Collectors.joining());
    }

    private void setColor() {
        commit.setFill(Color.web("#70ff7e"));
        rollback.setFill(Color.web("#ff6161"));
        commit.setOpacity(1);
        rollback.setOpacity(1);
    }

    private void resetColor() {
        commit.setFill(Color.web("#b2b2b2"));
        commit.setOpacity(0.5);
        rollback.setFill(Color.web("#b2b2b2"));
        rollback.setOpacity(0.5);
        btn_edit.setStyle("-fx-border-color:white");
        btn_delete.setStyle("-fx-border-color:white");
    }

    private void setDisable() {
        btn_edit.setDisable(true);
        btn_addStudent.setDisable(true);
        btn_delete.setDisable(true);
        con_edit.setDisable(true);
        con_addStudent.setDisable(true);
        con_delete.setDisable(true);
        txt_search.setDisable(true);
    }

    private void cleanDisable() {
        btn_edit.setDisable(false);
        btn_addStudent.setDisable(false);
        btn_delete.setDisable(false);
        con_edit.setDisable(false);
        con_addStudent.setDisable(false);
        con_delete.setDisable(false);
        txt_search.setDisable(false);
    }

    @Autowired
    private void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }
}
