package com.diplom.electronicrecord.controller.admin;


import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Teacher;
import com.diplom.electronicrecord.service.TeacherService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import com.diplom.electronicrecord.util.ValidationUtil;
import com.diplom.electronicrecord.view.admin.FxmlViewMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javax.validation.ValidationException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

@Controller
public class TeacherController implements Initializable {

    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();

    private Long teacherId;

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<Teacher> tableView;

    @FXML
    private TableColumn<Teacher, Teacher> fullName;

    @FXML
    private TableColumn<Teacher, String> category;

    @FXML
    private TableColumn<Teacher, String> login;

    @FXML
    private TableColumn<Teacher, Long> id;

    @FXML
    private TableColumn<Teacher, String> password;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private FontAwesomeIconView iconStatement;

    @FXML
    private JFXButton btn_commit;

    @FXML
    private JFXButton btn_rollback;

    @FXML
    private FontAwesomeIconView lbl_rollback;

    @FXML
    private FontAwesomeIconView lbl_commit;

    @FXML
    private TextField txt_fullName;

    @FXML
    private TextField txt_category;

    @FXML
    private TextField txt_login;

    @FXML
    private TextField txt_password;

    @FXML
    private JFXHamburger hamburger;

    private Boolean isReady = Boolean.FALSE;

    private final TeacherService teacherService;

    private final StageManager stageManager;

    private final FxmlViewMain main;

    private final SpringFXMLLoader springFXMLLoader;


    @Autowired
    public TeacherController(TeacherService teacherService,
                             @Lazy StageManager stageManager,
                             @Qualifier("Main") FxmlViewMain main,
                             SpringFXMLLoader springFXMLLoader) {
        this.teacherService = teacherService;
        this.stageManager = stageManager;
        this.main = main;
        this.springFXMLLoader = springFXMLLoader;

    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void exportCSV(ActionEvent event) {
        try {
            String path = AlertMaker.initFileChooserExport("CSV (разделители - точка с запятой) (*.csv)", "*.csv");
            if (!path.isEmpty()) {

                HandlerCSVUtil.exportFile(teacherService.getValuesCSV(teachers), teacherService.getHeaderCSV(), ';', path);
            }
        } catch (IOException e) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Ошибка при сохранение!", "Что-то пошло не так...");
        }
    }

    @FXML
    void handleCommit(ActionEvent event) {
        if (isReady) {

            List<Teacher> saveTeachers = new ArrayList<>();

            for (Teacher teacher : teachers) {
                if (teacher.getId() == 0) {
                    saveTeachers.add(teacher);
                }
            }

            try {
                teacherService.saveAll(saveTeachers);
                isReady = Boolean.FALSE;
                handleRefresh(new ActionEvent());
                AlertMaker.resetColorCommit(lbl_commit, lbl_rollback, btn_commit, btn_rollback);
            } catch (AlreadyExistException e) {
                AlertMaker.showMaterialDialog(rootPane, contentPane, "Найдено совпадение", e.getMessage());
            } catch (ValidationException ex) {
                AlertMaker.showMaterialDialog(rootPane, contentPane, "Данные не прошли проверку", "Проверьте ввели ли вы все данные");
            }

        }

    }

    @FXML
    void handleGenerateLogin(ActionEvent event) {

        int index = 0;
        int amount = 0;

        for (Teacher teacher : teachers) {
            if (ValidationUtil.isEmpty(teacher.getLogin())) {
                teacher.setLogin(generateLogin());
                teachers.set(index, teacher);
                amount++;
            }
            if (ValidationUtil.isEmpty(teacher.getPassword())) {
                teacher.setPassword(generatePassword());
                teachers.set(index, teacher);
                amount++;
            }
            index++;
        }
        if (amount == 0) {
            txt_login.setText(generateLogin());
            txt_password.setText(generatePassword());
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

    private void readerCSV(String path) {
        try {
            teachers.addAll(teacherService.rederCSV(path));
            AlertMaker.setColorCommitAndRollBack(lbl_commit, lbl_rollback, btn_commit, btn_rollback);
            isReady = Boolean.TRUE;
        } catch (IOException e) {
            AlertMaker.showErrorMessage("Ошибка при чтении файла", "Возможно файл поврежден или не тот формат");
        } catch (Exception e) {
            AlertMaker.showErrorMessage("Ошибка при чтении файла", "Возможно несовподение типов или пустой");
        }
    }


    @FXML
    void handleMouseClicked(MouseEvent event) {
        Teacher teacher = tableView.getSelectionModel().getSelectedItem();

        if (event.getClickCount() == 2 && teacher != null) {
            txt_fullName.setText(teacher.toString());
            txt_category.setText(teacher.getCategory());
            txt_login.setText(teacher.getLogin());
            txt_password.setText(teacher.getPassword());
            teacherId = teacher.getId();
            AlertMaker.setColorCommit(iconStatement, btn_edit);
            btn_edit.setDisable(false);
        }
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        AlertMaker.resetColorCommit(lbl_commit, lbl_rollback, btn_commit, btn_rollback);
        resetField();
        btn_edit.setDisable(true);
        AlertMaker.resetColorCommit(iconStatement, btn_edit);
        loadDataTeachers();
    }

    @FXML
    void handleRollback(ActionEvent event) {
        if (isReady) {
            handleRefresh(new ActionEvent());
        }
    }

    @FXML
    void handleStudentAddOption(ActionEvent event) {
        Teacher teacher = getTeacher();

        try {
            teacherService.save(teacher);
            handleRefresh(new ActionEvent());
            resetField();
        } catch (AlreadyExistException e) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    e.getMessage(), "");
        } catch (ValidationException e) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Ошибка при добавлении", "Данные не прошли проверку");
        }
    }

    @FXML
    void handleStudentDeleteOption(ActionEvent event) {
        Teacher teacher = tableView.getSelectionModel().getSelectedItem();
        if (teacher == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Преподаватель не выбран!", "Пожалуйста, выберите преподавателя");
            return;
        }
        Alert alert = new Alert(CONFIRMATION);
        alert.setTitle("Удаление Преподавателя ");
        alert.setHeaderText("Удалить Преподавателя " + teacher.toString() + " ?");
        alert.setContentText("Вы действительно хотите удалить преподавателя?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            teacherService.delete(teacher);
            handleRefresh(new ActionEvent());
        }
    }

    @FXML
    void handleStudentEditOption(ActionEvent event){
        if (teacherId != null) {
            Teacher teacher = getTeacher();
            teacher.setId(teacherId);
            try {
                teacherService.update(teacher);
                teacherId = null;
                handleRefresh(new ActionEvent());
            } catch (ValidationException | AlreadyExistException e) {
                AlertMaker.showMaterialDialog(rootPane, contentPane, "Ошибка при изменении", e.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AlertMaker.initDrawer(drawer, hamburger, springFXMLLoader);
        initCol();
        loadDataTeachers();
        changeTextField();
        btn_edit.setDisable(true);
        handleExistKey();
    }

    private void initCol() {
        id.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));

    }

    private void loadDataTeachers() {
        teachers.clear();
        teachers.addAll(teacherService.findAll());
        tableView.setItems(teachers);
    }

    private void changeTextField() {
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            teachers.clear();
            teachers.addAll(teacherService.findTeacherByLater(newValue));
        });
    }

    private void resetField() {
        txt_fullName.setText("");
        txt_password.setText("");
        txt_login.setText("");
        txt_category.setText("");
    }

    private Teacher getTeacher() {
        Teacher teacher = new Teacher();
        teacher.setCategory(txt_category.getText());
        teacher.setLogin(txt_login.getText());
        teacher.setPassword(txt_password.getText());
        try {
            String[] subString = txt_fullName.getText().split(" ");
            teacher.setName(subString[1]);
            teacher.setSurname(subString[0]);
            teacher.setPatronymic(subString[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, "Некорректное ФИО",
                    "ФИО должно быть разделено пробелом состоящие из 3 слов");
        }

        return teacher;
    }


    private String generatePassword() {
        return new Random().ints(7, 48, 58).mapToObj
                (i -> String.valueOf((char) i)).collect(Collectors.joining());
    }

    private String generateLogin() {
        return new Random().ints(5, 65, 90).mapToObj
                (i -> String.valueOf((char) i)).collect(Collectors.joining());
    }

    @FXML
    void handleOut(ActionEvent event) {
        stageManager.switchScene(main);
    }


    private void handleExistKey(){
        rootPane.addEventHandler(KeyEvent.KEY_PRESSED,event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                handleOut(new ActionEvent());
            }
        });

    }
}




