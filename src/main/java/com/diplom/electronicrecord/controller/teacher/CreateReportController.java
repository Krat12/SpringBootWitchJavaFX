package com.diplom.electronicrecord.controller.teacher;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.controller.LoginController;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Statement;
import com.diplom.electronicrecord.model.Subject;
import com.diplom.electronicrecord.model.Teacher;
import com.diplom.electronicrecord.service.MarkService;
import com.diplom.electronicrecord.service.StatementService;
import com.diplom.electronicrecord.service.SubjectService;
import com.diplom.electronicrecord.service.TeacherService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.view.teacher.FxmlViewMarkTeacher;
import com.jfoenix.controls.JFXDatePicker;
import impl.com.calendarfx.view.NumericTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

@Controller
public class CreateReportController implements Initializable {

    private static final String[] TYPE_MARK = {"Зачет", "Дифференцированный зачет", "Дипломная работа", "Курсовая работа"};

    ObservableList<String> typeList = FXCollections.observableArrayList(TYPE_MARK);

    ObservableList<Subject> subjects = FXCollections.observableArrayList();


    @FXML
    private ComboBox<Subject> cmb_subject;

    @FXML
    private ComboBox<String> cmb_typeMark;

    @FXML
    private JFXDatePicker creationDate;

    @FXML
    private NumericTextField txt_hours;

    private final SubjectService subjectService;

    private final StatementService statementService;

    private final TeacherService teacherService;

    private final MarkService markService;

    private final SpringFXMLLoader springFXMLLoader;

    private final FxmlViewMarkTeacher fxmlViewMarkTeacher;

    private final MarkControllerTeacher markControllerTeacher;

    private Group group;

    @Autowired
    public CreateReportController(SubjectService subjectService, StatementService statementService,
                                  TeacherService teacherService, MarkService markService,
                                  SpringFXMLLoader springFXMLLoader,
                                  @Qualifier("Mark") FxmlViewMarkTeacher fxmlViewMarkTeacher,
                                  MarkControllerTeacher markControllerTeacher) {
        this.subjectService = subjectService;
        this.statementService = statementService;
        this.teacherService = teacherService;
        this.markService = markService;
        this.springFXMLLoader = springFXMLLoader;
        this.fxmlViewMarkTeacher = fxmlViewMarkTeacher;
        this.markControllerTeacher = markControllerTeacher;
    }

    @FXML
    void cancel(ActionEvent event) {
        AlertMaker.closeStage(event);
    }

    @FXML
    void save(ActionEvent event) {
        if (checkData()) {
            Statement statement = getStatement();
            markService.insertMark(statement,getGroup().getId());
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            markControllerTeacher.setStatement(statement);
            markControllerTeacher.setGroup(getGroup());
            StageManager stageManager = new StageManager(springFXMLLoader,stage);
            stageManager.switchScene(fxmlViewMarkTeacher);
        }else {
            AlertMaker.showErrorMessage("Ошибка при создании", "Данные не прошли проверку");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmb_typeMark.setItems(typeList);
        loadDataInComboBoxSubject();
        creationDate.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private void loadDataInComboBoxSubject(){
        subjects.clear();
        subjects.addAll(subjectService.findSubjectsByGroupAndTeacher(getGroup().getId(), LoginController.getUserId()));
        cmb_subject.setItems(subjects);
    }

    private Statement getStatement() {
        Statement statement = new Statement();
        Date date = Date.from(creationDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        statement.setDate(date);
        statement.setHours(Integer.valueOf(txt_hours.getText()));
        statement.setType(cmb_typeMark.getSelectionModel().getSelectedItem());
        statement.setGroup(getGroup());
        statement.setSubject(cmb_subject.getSelectionModel().getSelectedItem());
        Teacher teacher = teacherService.findTeacherById(LoginController.getUserId());
        statement.setTeacher(teacher);
        return statementService.save(statement) ;
    }

    private boolean checkData() {
        if (txt_hours.getText().trim().isEmpty()) {
            return false;
        }else if(Integer.valueOf(txt_hours.getText()) > 1000){return false;}

        if (cmb_subject.getSelectionModel().getSelectedItem() == null) {
            return false;
        }

        if (cmb_typeMark.getSelectionModel().getSelectedItem().equals("Тип оценки")) {
            return false;
        }
        if (creationDate.getValue() == null) {
            return false;
        }

        return true;
    }
}

