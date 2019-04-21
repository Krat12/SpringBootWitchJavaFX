package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.service.GroupService;
import com.diplom.electronicrecord.service.MarkService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.*;

@Controller
public class StudentStatisticController implements Initializable {

    private final ObservableList<Group> listGroups = FXCollections.observableArrayList();

    private final int FIRST_SEMESTER = 4;// number of the month

    private final int SECOND_SEMESTER = 8;

    private int checkSelectCourse = 0;

    @FXML
    private Tab tab_student;

    @FXML
    private JFXToggleButton tab_firsSemester;

    @FXML
    private JFXToggleButton tab_secondSemester;

    @FXML
    private TableView<Group> tb_groups;

    @FXML
    private TableColumn<Group, String> col_groupName;

    @FXML
    private Label caption;

    @FXML
    private PieChart chart;

    @FXML
    private HBox txt_stub;

    @FXML
    private Text txt_text;

    private final GroupService groupService;

    private final MarkService markService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        showGroupCurrentYear();
    }

    @Autowired
    public StudentStatisticController(GroupService groupService, MarkService markService) {
        this.groupService = groupService;
        this.markService = markService;
    }

    @FXML
    void handleCreateDiagram(ActionEvent event) {
        if (checkSelectSemester()) {

            Group course = tb_groups.getSelectionModel().getSelectedItem();

            if (checkSelectGroup(course)) {

                MarkCurrentYear(course);
                MarkFourCourse(course);
                MarkThreeCourse(course);
                MarkTwoCourse(course);
                MarkOneCourse(course);
            }
        }
    }

    private void MarkFourCourse(Group group) {
        if (checkSelectCourse == 4) {

            if (tab_firsSemester.isSelected()) {
                Calendar start = new GregorianCalendar(group.getYear() + 3, Calendar.SEPTEMBER, 1);
                Calendar end = new GregorianCalendar(group.getYear() + 3, Calendar.SEPTEMBER, 1);
                end.add(Calendar.MONTH, FIRST_SEMESTER);
                markService.findMarksByDateAndGroupId(start.getTime(), end.getTime(), group.getId());
                drawChart();

            }
            if (tab_secondSemester.isSelected()) {
                Calendar start = new GregorianCalendar(group.getYear() + 4, Calendar.JANUARY, 1);
                Calendar end = new GregorianCalendar(group.getYear() + 4, Calendar.SEPTEMBER, 1);
                end.add(Calendar.MONTH, SECOND_SEMESTER);
                markService.findMarksByDateAndGroupId(start.getTime(), end.getTime(), group.getId());
                drawChart();
            }
        }
    }

    private void MarkThreeCourse(Group group) {
        if (checkSelectCourse == 3) {

            if (tab_firsSemester.isSelected()) {
                Calendar start = new GregorianCalendar(group.getYear() + 2, Calendar.SEPTEMBER, 1);
                Calendar end = new GregorianCalendar(group.getYear() + 2, Calendar.SEPTEMBER, 1);
                end.add(Calendar.MONTH, FIRST_SEMESTER);
                markService.findMarksByDateAndGroupId(start.getTime(), end.getTime(), group.getId());
                drawChart();

            }
            if (tab_secondSemester.isSelected()) {
                Calendar start = new GregorianCalendar(group.getYear() + 3, Calendar.JANUARY, 1);
                Calendar end = new GregorianCalendar(group.getYear() + 3, Calendar.SEPTEMBER, 1);
                end.add(Calendar.MONTH, SECOND_SEMESTER);
                markService.findMarksByDateAndGroupId(start.getTime(), end.getTime(), group.getId());
                drawChart();
            }
        }
    }

    private void MarkTwoCourse(Group group) {
        if (checkSelectCourse == 2) {

            if (tab_firsSemester.isSelected()) {
                Calendar start = new GregorianCalendar(group.getYear() + 1, Calendar.SEPTEMBER, 1);
                Calendar end = new GregorianCalendar(group.getYear() + 1, Calendar.SEPTEMBER, 1);
                end.add(Calendar.MONTH, FIRST_SEMESTER);
                markService.findMarksByDateAndGroupId(start.getTime(), end.getTime(), group.getId());
                drawChart();

            }
            if (tab_secondSemester.isSelected()) {
                Calendar start = new GregorianCalendar(group.getYear() + 2, Calendar.JANUARY, 1);
                Calendar end = new GregorianCalendar(group.getYear() + 2, Calendar.SEPTEMBER, 1);
                end.add(Calendar.MONTH, SECOND_SEMESTER);
                markService.findMarksByDateAndGroupId(start.getTime(), end.getTime(), group.getId());
                drawChart();
            }
        }
    }

    private void MarkOneCourse(Group group) {
        if (checkSelectCourse == 1) {

            if (tab_firsSemester.isSelected()) {
                Calendar start = new GregorianCalendar(group.getYear(), Calendar.SEPTEMBER, 1);
                Calendar end = new GregorianCalendar(group.getYear(), Calendar.SEPTEMBER, 1);
                end.add(Calendar.MONTH, FIRST_SEMESTER);
                markService.findMarksByDateAndGroupId(start.getTime(), end.getTime(), group.getId());
                drawChart();

            }
            if (tab_secondSemester.isSelected()) {
                Calendar start = new GregorianCalendar(group.getYear() + 1, Calendar.JANUARY, 1);
                Calendar end = new GregorianCalendar(group.getYear() + 1, Calendar.SEPTEMBER, 1);
                end.add(Calendar.MONTH, SECOND_SEMESTER);
                markService.findMarksByDateAndGroupId(start.getTime(), end.getTime(), group.getId());
                drawChart();
            }
        }
    }

    private void MarkCurrentYear(Group group) {
        if (checkSelectCourse == 0) {
            int year;

            if (tab_firsSemester.isSelected()) {

                Calendar calendar =Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                calendar.setTime(new Date());

                if(calendar.get(Calendar.MONTH) >= 8 && calendar.get(Calendar.MONTH) <= 11){
                    year = calendar.get(Calendar.YEAR);
                }else {
                    calendar.add(Calendar.YEAR,-1);
                    year = calendar.get(Calendar.YEAR);
                }

                Calendar start = new GregorianCalendar(year, Calendar.SEPTEMBER, 1);
                Calendar end = new GregorianCalendar(year, Calendar.SEPTEMBER, 1);
                end.add(Calendar.MONTH,FIRST_SEMESTER);
                markService.findMarksByDateAndGroupId(start.getTime(), end.getTime(), group.getId());
                drawChart();

            }

            if (tab_secondSemester.isSelected()) {
                Calendar calendar =Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                calendar.setTime(new Date());

                if(calendar.get(Calendar.MONTH) >= 0 && calendar.get(Calendar.MONTH) <= 7){
                    year = calendar.get(Calendar.YEAR);
                }else {
                    calendar.add(Calendar.YEAR,1);
                    year = calendar.get(Calendar.YEAR);
                }

                Calendar start = new GregorianCalendar(year, Calendar.JANUARY, 1);
                Calendar end = new GregorianCalendar(year, Calendar.JANUARY, 1);
                end.add(Calendar.MONTH,SECOND_SEMESTER);
                markService.findMarksByDateAndGroupId(start.getTime(), end.getTime(), group.getId());
                drawChart();
            }
        }
    }



    @FXML
    void handleCurrentCourse(ActionEvent event) {
        setSelectCourse(0);
        showGroupCurrentYear();
    }

    @FXML
    void handleFirstSemester(ActionEvent event) {
        tab_secondSemester.setSelected(false);
    }

    @FXML
    void handleFourCourse(ActionEvent event) {
        setSelectCourse(4);
        showGroupByCourse(4);

    }

    @FXML
    void handleOneCourse(ActionEvent event) {
        setSelectCourse(1);
        showGroupByCourse(1);
    }

    @FXML
    void handleSecondSemester(ActionEvent event) {
        tab_firsSemester.setSelected(false);
    }

    @FXML
    void handleThreeCourse(ActionEvent event) {
        setSelectCourse(3);
        showGroupByCourse(3);
    }

    @FXML
    void handleTwoCourse(ActionEvent event) {
        setSelectCourse(2);
        showGroupByCourse(2);
    }

    private void showGroupByCourse(int course) {
        int startYear = getCurrentYearForGroup() - course;
        listGroups.clear();
        listGroups.addAll(groupService.findByYearAndStatus(startYear, "обучаются"));
        tb_groups.setItems(listGroups);
    }

    private void showGroupCurrentYear(){
        Calendar calendar =Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new Date());
        listGroups.clear();
        listGroups.addAll(groupService.findByYearLessThanEqualAndStatus(calendar.get(Calendar.YEAR), "обучаются"));
        tb_groups.setItems(listGroups);
    }

    private void initCol() {
        col_groupName.setCellValueFactory(new PropertyValueFactory<>("groupName"));
    }

    private void setSelectCourse(int course) {
        checkSelectCourse = course;
    }

    private int getCurrentYearForGroup() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new Date());
        if (calendar.get(Calendar.MONTH) >= 8) {
            calendar.add(Calendar.YEAR, 1);
        }
        return calendar.get(Calendar.YEAR);
    }

    private boolean checkSelectSemester() {
        if (!tab_firsSemester.isSelected() && !tab_secondSemester.isSelected()) {
            AlertMaker.showErrorMessage("Семестр не выбран", "Пожалуйста, выберите семестр");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkSelectGroup(Group group) {

        if (group != null) {
            return true;
        } else {
            AlertMaker.showErrorMessage("Группа не выбрана", "Пожалуйста, выберите группа");
            return false;
        }
    }

    private void drawChart() {
        chart.getData().clear();
        chart.setData(getDataChart());
        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setText(String.valueOf(data.getPieValue()));
                }
            });
        }
        checkDataChart();
    }

    private ObservableList<PieChart.Data> getDataChart() {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        if (markService.getCountHonours() != 0) {
            PieChart.Data excellent = new PieChart.Data("Отличники", markService.getCountHonours());
            pieChartData.add(excellent);
        }
        if (markService.getCountGoodStudents() != 0) {
            PieChart.Data good = new PieChart.Data("Хорошисты", markService.getCountGoodStudents());
            pieChartData.add(good);
        }

        if (markService.getCountAcceptable() != 0) {
            PieChart.Data soSo = new PieChart.Data("Троечники", markService.getCountAcceptable());
            pieChartData.add(soSo);
        }

        if (markService.getCountBadStudents() != 0) {
            PieChart.Data bad = new PieChart.Data("Двоечники", markService.getCountBadStudents());
            pieChartData.add(bad);
        }

        if (markService.getCountOther() != 0) {
            PieChart.Data other = new PieChart.Data("Другие", markService.getCountOther());
            pieChartData.add(other);
        }
        return pieChartData;
    }

    private void checkDataChart() {
        if (markService.getCountHonours() == 0 && markService.getCountGoodStudents() == 0
                && markService.getCountAcceptable() == 0 && markService.getCountBadStudents() == 0
                && markService.getCountOther() == 0) {
            txt_text.setText("Данных нет");
            txt_stub.setVisible(true);

        } else {
            txt_stub.setVisible(false);
            chart.setVisible(true);
        }
    }

}

