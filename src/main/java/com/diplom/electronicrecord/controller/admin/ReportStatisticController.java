package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.model.*;
import com.diplom.electronicrecord.service.MarkService;
import com.diplom.electronicrecord.service.StatementService;
import com.diplom.electronicrecord.service.TeacherService;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ReportStatisticController implements Initializable {

    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();

    private ObservableList<StatisticReportModel> reportModels = FXCollections.observableArrayList();

    @FXML
    private TableView<Teacher> tb_teacher;

    @FXML
    private TableColumn<Teacher, Teacher> col_teacherName;

    @FXML
    private TableView<StatisticReportModel> tb_date;

    @FXML
    private JFXToggleButton tab_firstSemesterST;

    @FXML
    private JFXToggleButton tab_secondSemesterST;

    @FXML
    private TableColumn<StatisticReportModel, Group> col_group;

    @FXML
    private TableColumn<StatisticReportModel, Subject> col_subject;

    @FXML
    private TableColumn<StatisticReportModel, Integer> col_allStudent;

    @FXML
    private TableColumn<StatisticReportModel, Integer> col_excellentStudent;

    @FXML
    private TableColumn<StatisticReportModel, Integer> col_goodStudent;

    @FXML
    private TableColumn<StatisticReportModel, Integer> col_acceptableStudents;

    @FXML
    private TableColumn<StatisticReportModel, Integer> col_loserStudents;

    @FXML
    private TableColumn<StatisticReportModel, Integer> col_advanced;

    @FXML
    private TableColumn<StatisticReportModel, Double> col_avg;

    private final TeacherService teacherService;

    private StatementService statementService;

    private Integer countExellent;

    private Integer countGood;

    private Integer countAcceptable;

    private Integer countAllStudents;

    private MarkService markService;

    @Autowired
    public ReportStatisticController(TeacherService teacherService, StatementService statementService, MarkService markService) {
        this.teacherService = teacherService;
        this.statementService = statementService;
        this.markService = markService;
    }

    @FXML
    void handleFirstSemester(ActionEvent event) {
        tab_secondSemesterST.setSelected(false);
    }

    @FXML
    void handleGenerateReport(ActionEvent event) {

    }

    @FXML
    void handleSecondSemester(ActionEvent event) {
        tab_firstSemesterST.setSelected(false);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadDataTeacherInTable();
    }

    private void initCol() {
        col_teacherName.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        col_group.setCellValueFactory(new PropertyValueFactory<>("group"));
        col_subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_allStudent.setCellValueFactory(new PropertyValueFactory<>("allStudent"));
        col_excellentStudent.setCellValueFactory(new PropertyValueFactory<>("excellent"));
        col_goodStudent.setCellValueFactory(new PropertyValueFactory<>("good"));
        col_acceptableStudents.setCellValueFactory(new PropertyValueFactory<>("acceptable"));
        col_loserStudents.setCellValueFactory(new PropertyValueFactory<>("loser"));
        col_advanced.setCellValueFactory(new PropertyValueFactory<>("advance"));
        col_avg.setCellValueFactory(new PropertyValueFactory<>("avg"));

    }

    private void loadDataTeacherInTable(){
        teachers.clear();
        teachers.addAll(teacherService.findAll());
        tb_teacher.setItems(teachers);
    }

    private void loadDataReportInTable(Long teacherId){
        for (Statement statement : statementService.findStatementByTeacherId(teacherId)) {
            StatisticReportModel statisticReportModel = new StatisticReportModel();
            statisticReportModel.setSubject(statement.getSubject());
            statisticReportModel.setGroup(statement.getGroup());
            findMarkByStatementId(statement.getId());
        }
    }

    private void findMarkByStatementId(Long id){
        int number = 1;
        for (Marks marks: markService.findMarksByStatementId(id)) {

            if(marks.getMark() == 5){
                countExellent++;
            }
            if(marks.getMark() == 4){
                countGood++;
            }

            if(marks.getMark() == 3){
                countAcceptable++;
            }
            number++;
        }
        countAllStudents = number;
    }




    private class StatisticReportModel{

        private Subject subject;

        private Group group;

        private IntegerProperty allStudent;

        private IntegerProperty excellent;

        private IntegerProperty good;

        private IntegerProperty acceptable;

        private IntegerProperty loser;

        private IntegerProperty advance;

        private DoubleProperty avg;

        public Subject getSubject() {
            return subject;
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        public Group getGroup() {
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public Integer getAllStudent() {
            return allStudent.getValue();
        }

        public void setAllStudent(Integer allStudent) {
            this.allStudent = new SimpleIntegerProperty(allStudent);
        }

        public Integer getExcellent() {
            return excellent.getValue();
        }

        public void setExcellent(Integer excellent) {
            this.excellent = new SimpleIntegerProperty(excellent);
        }

        public Integer getGood() {
            return good.getValue();
        }

        public void setGood(Integer good) {
            this.good = new SimpleIntegerProperty(good);
        }

        public Integer getAcceptable() {
            return acceptable.getValue();
        }

        public void setAcceptable(Integer acceptable) {
            this.acceptable = new SimpleIntegerProperty(acceptable);
        }

        public Integer getLoser() {
            return loser.getValue();
        }

        public void setLoser(Integer loser) {
            this.loser = new SimpleIntegerProperty(loser);
        }

        public Integer getAdvance() {
            return advance.getValue();
        }

        public void setAdvance(Integer advance) {
            this.advance = new SimpleIntegerProperty(advance);
        }

        public Double getAvg() {
            return avg.getValue();
        }

        public void setAvg(Double avg) {
            this.avg = new SimpleDoubleProperty(avg);
        }
    }
}

