package com.diplom.electronicrecord.controller.teacher;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.controller.LoginController;
import com.diplom.electronicrecord.model.*;
import com.diplom.electronicrecord.service.MarkService;
import com.diplom.electronicrecord.service.StudentService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.view.teacher.FxmlViewMarkTeacher;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class EditMarkController implements Initializable {


    private ObservableList<Student> students = FXCollections.observableArrayList();

    private ObservableList<Marks> marks = FXCollections.observableArrayList();

    private static final String[] STRING_MARKS = {"Зачет", "Незачет"};

    private static final String[] NUMBER_MARKS = {"5", "4", "3", "2", "1", "н/a"};

    private ObservableList<String> pass = FXCollections.observableArrayList();

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TextField txt_serchStudent;

    @FXML
    private JFXButton btn_commit;

    @FXML
    private FontAwesomeIconView commit;

    @FXML
    private FontAwesomeIconView lbl_edit;

    @FXML
    private JFXButton btn_rollback;

    @FXML
    private FontAwesomeIconView rollback;

    @FXML
    private JFXButton btn_editStudent;

    @FXML
    private TableView<Student> tb_students;

    @FXML
    private TableColumn<Student, Integer> col_NumberStudent;

    @FXML
    private TableColumn<Student, String> col_FullName;

    @FXML
    private TableView<Marks> tb_achievement;

    @FXML
    private TableColumn<Marks, Subject> col_Subject;

    @FXML
    private TableColumn<Marks, String> col_typeCertification;

    @FXML
    private TableColumn<Marks, String> col_Mark;

    private final StudentService studentService;

    private final MarkService markService;

    private Boolean isReady;

    private Map<Integer, Marks> map = new HashMap<>();

    private final SpringFXMLLoader springFXMLLoader;

    private FxmlViewMarkTeacher courseWork;

    private FxmlViewMarkTeacher diplom;

    private EditDiplomController editDiplomController;

    private EditCourseWorkController editCourseWorkController;

    @Autowired
    public EditMarkController(StudentService studentService, MarkService markService,
                              EditCourseWorkController editCourseWorkController, SpringFXMLLoader springFXMLLoader,
                              @Qualifier("CourseWork") FxmlViewMarkTeacher courseWork,
                              @Qualifier("EditDiplom") FxmlViewMarkTeacher diplom,
                              EditDiplomController editDiplomController,
                              EditCourseWorkController editCourseWorkController1) {
        this.studentService = studentService;
        this.markService = markService;
        this.springFXMLLoader = springFXMLLoader;
        this.courseWork = courseWork;
        this.diplom = diplom;
        this.editDiplomController = editDiplomController;
        this.editCourseWorkController = editCourseWorkController1;
    }

    @FXML
    void handleCommit(ActionEvent event) {
        if(isReady){
            for (Map.Entry<Integer, Marks> mark: map.entrySet()) {
                Marks marks = mark.getValue();
                marks.setMark(getMarkInt(marks.getMarkString()));
                markService.update(marks);
            }
            map.clear();
            AlertMaker.resetColorCommit(commit, rollback, btn_commit, btn_rollback);
            loadMarksInTable(tb_students.getSelectionModel().getSelectedItem().getId());

        }
    }

    @FXML
    void handleEdit(ActionEvent event) {
        Marks marks = tb_achievement.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        stage.setResizable(false);
        if (marks.getStatement().getType().equals("Дипломная работа")) {
            editDiplomController.setDiplom((Diplom) marks);
            StageManager stageManager = new StageManager(springFXMLLoader,new Stage());
            stageManager.switchScene(diplom);
        }
        if (marks.getStatement().getType().equals("Курсовая работа")) {
            editCourseWorkController.setCourseWork((CourseWork) marks);
            StageManager stageManager = new StageManager(springFXMLLoader,new Stage());
            stageManager.switchScene(courseWork);
        }
    }

    @FXML
    void handleKeyAchievement(KeyEvent event) {
        Marks marks = tb_achievement.getSelectionModel().getSelectedItem();
        if (marks != null) {

            if (marks.getStatement().getType().equals("Зачет")) {

                setMarkPassedToFail(event.getCode().getName(), marks);

            } else {

                setMarkOneToFive(event.getCode().getName(), marks);
            }
        }
    }

    private void setMarkOneToFive(String key, Marks marks) {
        if (key.equals("1")) {
            marks.setMarkString(key);
            updateMark(marks);
        }

        if (key.equals("2")) {
            marks.setMarkString(key);
            updateMark(marks);
        }

        if (key.equals("3")) {
            marks.setMarkString(key);
            updateMark(marks);
        }
        if (key.equals("4")) {
            marks.setMarkString(key);
            updateMark(marks);
        }
        if (key.equals("5")) {
            marks.setMarkString(key);
            updateMark(marks);
        }
        if (key.equals("0")) {
            marks.setMarkString("н/а");
            updateMark(marks);
        }

    }

    private void setMarkPassedToFail(String key, Marks marks) {
        if (key.equals("1")) {
            marks.setMarkString("Зачет");
            updateMark(marks);
        }

        if (key.equals("2")) {
            marks.setMarkString("Незачет");
            updateMark(marks);
        }
    }

    @FXML
    void handleKeyPressed(KeyEvent event) {

    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadStudentInTable();
    }

    @FXML
    void handleRollBack(ActionEvent event) {

        if(isReady){
            Student student = tb_students.getSelectionModel().getSelectedItem();
            loadMarksInTable(student.getId());
            isReady = Boolean.FALSE;
            AlertMaker.resetColorCommit(commit, rollback, btn_commit, btn_rollback);
        }
    }

    @FXML
    void handleSelectAchievement(MouseEvent event) {
        Marks mark = tb_achievement.getSelectionModel().getSelectedItem();
        if (mark != null) {
            if (mark.getStatement().getType().equals("Зачет")) {
                pass.clear();
                pass.addAll(STRING_MARKS);
            } else {
                pass.clear();
                pass.addAll(NUMBER_MARKS);
            }

            if (mark.getStatement().getType().equals("Дипломная работа")||
                    mark.getStatement().getType().equals("Курсовая работа")) {
                AlertMaker.setColorCommit(lbl_edit,btn_editStudent);
                btn_editStudent.setDisable(false);
            }else {
                AlertMaker.resetColorCommit(lbl_edit,btn_editStudent);
                btn_editStudent.setDisable(true);
            }


        }
    }

    @FXML
    void handleSelectStudent(MouseEvent event) {
        Student student = tb_students.getSelectionModel().getSelectedItem();
        if(student != null){
            loadMarksInTable(student.getId());
            AlertMaker.resetColorCommit(commit, rollback, btn_commit, btn_rollback);
            isReady = Boolean.FALSE;
            map.clear();
        }
    }

    private void loadStudentInTable() {
        students.clear();
        int number = 1;
        for (Student student: studentService.findStudentsByTeacherId(LoginController.getUserId())) {
            student.setNumberStudent(number);
            student.setFullName(student.getSurname()+" "+student.getName()+" "+student.getPatronymic());
            number++;
            students.add(student);
        }
        tb_students.setItems(students);
    }

    private void loadMarksInTable(Long studentId){
        marks.clear();
        for (Marks mark : markService.findMarksByStudentId(studentId,LoginController.getUserId())) {
            mark.setSubject(mark.getStatement().getSubject());
            if(mark.getStatement().getType().equals("Зачет")){
                mark.setMarkString(convertIntToStringPassOrFail(mark.getMark()));
            }else {
                mark.setMarkString(convertIntToString(mark.getMark()));
            }

            marks.add(mark);
        }
        tb_achievement.setItems(marks);
    }

    private String convertIntToStringPassOrFail(Integer mark){
        if(mark == 0){
            return "Незачет";
        }else if(mark == 1){
            return "Зачет";
        }
        return "";
    }

    private String convertIntToString(Integer mark){
        if(mark == 0){
            return "н/а";
        }else if(mark == -1){
            return "";
        }
        return String.valueOf(mark);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadStudentInTable();
        comboBoxMark();
        btn_editStudent.setDisable(true);
        changeTextField();
    }

    protected void initCol() {
        col_NumberStudent.setCellValueFactory(new PropertyValueFactory<>("numberStudent"));
        col_FullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col_Mark.setCellValueFactory(new PropertyValueFactory<>("markString"));
        col_Subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_typeCertification.setCellValueFactory(new PropertyValueFactory<>("statement"));
    }

    private void comboBoxMark() {
        tb_achievement.setEditable(true);
        col_Mark.setCellFactory(ComboBoxTableCell.forTableColumn(pass));

        col_Mark.setOnEditCommit((TableColumn.CellEditEvent<Marks, String> event) -> {
            if (event.getNewValue() == null) {
               return;
            }

            String mark = event.getNewValue();

                TablePosition<Marks, String> pos = event.getTablePosition();
                int row = pos.getRow();
                Marks marks = event.getTableView().getItems().get(row);
                marks.setMarkString(mark);
                map.put(row, marks);
                isReady = Boolean.TRUE;
                AlertMaker.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);

        });
    }
    private void updateMark(Marks mark) {
        int index = tb_achievement.getSelectionModel().getSelectedIndex();
        map.put(index,mark);
        AlertMaker.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
        isReady = Boolean.TRUE;
        marks.set(index, mark);
    }

    private Integer getMarkInt(String markString){
        if (markString.equals("н/а")) {
            return 0;
        } else if (markString.equals("Зачет")) {
            return 1;
        } else if (markString.equals("Незачет")) {
            return 0;
        } else {
            return Integer.valueOf(markString);
        }
    }

    private void changeTextField() {
        txt_serchStudent.textProperty().addListener((observable, oldValue, newValue) -> {
            students.clear();
            int number = 1;
            for (Student student : studentService.findStudentsByTeacherIdAndStartWitch(LoginController.getUserId(),newValue)) {
                student.setNumberStudent(number);
                student.setFullName(student.getSurname()+" "+student.getName()+" "+student.getPatronymic());
                number++;
                students.add(student);
            }
        });
    }

}

