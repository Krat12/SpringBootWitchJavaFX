package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.model.*;
import com.diplom.electronicrecord.service.MarkService;
import com.diplom.electronicrecord.service.StatementService;
import com.diplom.electronicrecord.service.TeacherService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.jfoenix.controls.JFXToggleButton;
import impl.com.calendarfx.view.NumericTextField;
import javafx.beans.property.*;
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
import java.text.DecimalFormat;
import java.util.*;

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

    @FXML
    private NumericTextField txt_year;

    private final TeacherService teacherService;

    private StatementService statementService;

    private Integer countExcellent = 0;

    private Integer countGood = 0;

    private Integer countAcceptable = 0;

    private Integer countAllStudents = 0;

    private Double average = 0.0;

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
        Teacher teacher = tb_teacher.getSelectionModel().getSelectedItem();
        if (checkSelectTeacher(teacher)) {
            if (checkSelectSemester()) {
                reportFirstSemester(teacher);
                reportSecondSemester(teacher);
            }
        }
    }

    private boolean checkSelectSemester() {
        if (!tab_firstSemesterST.isSelected() && !tab_secondSemesterST.isSelected()) {
            AlertMaker.showErrorMessage("Семестр не выбран", "Пожалуйста, выберите семестр");
            return false;
        } else {
            return true;
        }
    }

    @FXML
    void handleSecondSemester(ActionEvent event) {
        tab_firstSemesterST.setSelected(false);
    }


    private void reportFirstSemester(Teacher teacher) {

        if (tab_firstSemesterST.isSelected()) {

            if (txt_year.getText().trim().isEmpty()) {
                Calendar start = new GregorianCalendar(getCurrentYearFirstSemester(),Calendar.SEPTEMBER,1);
                Calendar end = new GregorianCalendar(getCurrentYearFirstSemester()+1,Calendar.JANUARY,1);
                loadDataReportInTable(teacher.getId(),start.getTime(),end.getTime());
            } else {
                Calendar start = new GregorianCalendar(Integer.valueOf(txt_year.getText()),Calendar.SEPTEMBER,1);
                Calendar end = new GregorianCalendar(Integer.valueOf(txt_year.getText())+1,Calendar.JANUARY,1);
                loadDataReportInTable(teacher.getId(),start.getTime(),end.getTime());
            }
        }
    }

    private void reportSecondSemester(Teacher teacher) {

        if (tab_secondSemesterST.isSelected()) {

            if (txt_year.getText().trim().isEmpty()) {
                Calendar start = new GregorianCalendar(getCurrentYearSecondSemester(),Calendar.JANUARY,1);
                Calendar end = new GregorianCalendar(getCurrentYearSecondSemester(),Calendar.AUGUST,31);
                loadDataReportInTable(teacher.getId(),start.getTime(),end.getTime());
            } else {
                Calendar start = new GregorianCalendar(Integer.valueOf(txt_year.getText()),Calendar.JANUARY, 1);
                Calendar end = new GregorianCalendar(Integer.valueOf(txt_year.getText()),Calendar.AUGUST,31);
                loadDataReportInTable(teacher.getId(),start.getTime(),end.getTime());
            }
        }
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

    private void loadDataTeacherInTable() {
        teachers.clear();
        teachers.addAll(teacherService.findAll());
        tb_teacher.setItems(teachers);
    }

    private void loadDataReportInTable(Long teacherId,Date start, Date end) {
        reportModels.clear();
        for (Statement statement : statementService.findStatementByTeacherId(teacherId,start,end)) {
            StatisticReportModel statisticReportModel = new StatisticReportModel();
            statisticReportModel.setSubject(statement.getSubject());
            statisticReportModel.setGroup(statement.getGroup());
            findMarkByStatementId(statement.getId());
            statisticReportModel.setAcceptable(countAcceptable);
            statisticReportModel.setGood(countGood);
            statisticReportModel.setExcellent(countExcellent);
            statisticReportModel.setAllStudent(countAllStudents);
            statisticReportModel.setLoser(getLoserStudent());
            statisticReportModel.setAdvance(getAdvance());
            DecimalFormat format = new DecimalFormat("#.00");
            statisticReportModel.setAvg(format.format(average));
            reportModels.add(statisticReportModel);
            resetField();
        }
        tb_date.setItems(reportModels);
    }

    private void findMarkByStatementId(Long id) {
        int number = 0;
        List<Marks> marksList = markService.findMarksByStatementId(id);
        for (Marks marks : marksList) {

            if (marks.getMark() == 5) {
                countExcellent++;
            }
            if (marks.getMark() == 4) {
                countGood++;
            }

            if (marks.getMark() == 3) {
                countAcceptable++;
            }
            number++;
        }
        countAllStudents = number;
        average = markService.getAverage(marksList);
    }

    private Integer getLoserStudent() {
        return countAllStudents - countExcellent - countGood - countAcceptable;
    }

    private Integer getAdvance() {
        return countExcellent + countGood + countAcceptable;
    }

    private void resetField(){
        countAcceptable = 0;
        countGood = 0;
        countExcellent = 0;
        countAllStudents = 0;
        average = 0.0;
    }

    private Boolean checkSelectTeacher(Teacher teacher) {
        if (teacher != null) {
            return true;
        } else {
            AlertMaker.showErrorMessage("Преподаватель не выбран",
                    "Пожалуйста, выберите преподавателя");
            return false;
        }
    }

    private int getCurrentYearFirstSemester(){
        Calendar calendar =Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new Date());

        if(calendar.get(Calendar.MONTH) >= 8 && calendar.get(Calendar.MONTH) <= 11){
           return  calendar.get(Calendar.YEAR);
        }else {
            calendar.add(Calendar.YEAR,-1);
           return calendar.get(Calendar.YEAR);
        }
    }
    private int getCurrentYearSecondSemester(){
        Calendar calendar =Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new Date());

        if(calendar.get(Calendar.MONTH) >= 0 && calendar.get(Calendar.MONTH) <= 7){
            return calendar.get(Calendar.YEAR);
        }else {
            calendar.add(Calendar.YEAR,1);
            return calendar.get(Calendar.YEAR);
        }
    }

    public class StatisticReportModel {

        private Subject subject;

        private Group group;

        private IntegerProperty allStudent;

        private IntegerProperty excellent;

        private IntegerProperty good;

        private IntegerProperty acceptable;

        private IntegerProperty loser;

        private IntegerProperty advance;

        private StringProperty avg;

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

        void setAllStudent(Integer allStudent) {
            this.allStudent = new SimpleIntegerProperty(allStudent);
        }

        public Integer getExcellent() {
            return excellent.getValue();
        }

        void setExcellent(Integer excellent) {
            this.excellent = new SimpleIntegerProperty(excellent);
        }

        public Integer getGood() {
            return good.getValue();
        }

        void setGood(Integer good) {
            this.good = new SimpleIntegerProperty(good);
        }

        public Integer getAcceptable() {
            return acceptable.getValue();
        }

        void setAcceptable(Integer acceptable) {
            this.acceptable = new SimpleIntegerProperty(acceptable);
        }

        public Integer getLoser() {
            return loser.getValue();
        }

        void setLoser(Integer loser) {
            this.loser = new SimpleIntegerProperty(loser);
        }

        public Integer getAdvance() {
            return advance.getValue();
        }

        void setAdvance(Integer advance) {
            this.advance = new SimpleIntegerProperty(advance);
        }

        public String getAvg() {
            return avg.getValue();
        }

        void setAvg(String avg) {
            this.avg = new SimpleStringProperty(avg);
        }
    }
}

