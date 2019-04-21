package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.controller.LoginController;
import com.diplom.electronicrecord.controller.teacher.MarkControllerTeacher;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Statement;
import com.diplom.electronicrecord.model.Subject;
import com.diplom.electronicrecord.model.Teacher;
import com.diplom.electronicrecord.service.GroupService;
import com.diplom.electronicrecord.service.StatementService;
import com.diplom.electronicrecord.service.SubjectService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.view.admin.FxmlViewReport;
import com.diplom.electronicrecord.view.teacher.FxmlViewMarkTeacher;
import com.diplom.electronicrecord.view.teacher.FxmlViewReportTeacher;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

@Controller
public class StatementController implements Initializable {

    private final ObservableList<Group> listGroups = FXCollections.observableArrayList();

    private final ObservableList<Subject> listSubjects = FXCollections.observableArrayList();

    private static final String[] TYPE_MARK = {"Зачет", "Дифференцированный зачет", "Дипломная работа", "Курсовая работа"};

    private final ObservableList<String> listTypeCertification = FXCollections.observableArrayList(TYPE_MARK);

    private final ObservableList<Statement> statements = FXCollections.observableArrayList();

    private Date dateTo = null;

    private Date dateFrom = null;

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TableView<Statement> tableView;


    @FXML
    private TableColumn<Statement, Group> col_group;

    @FXML
    private TableColumn<Statement, Subject> colSubject;

    @FXML
    private TableColumn<Statement, Teacher> colTeacher;

    @FXML
    private TableColumn<Statement, String> colType;

    @FXML
    private TableColumn<Statement, Integer> colHours;

    @FXML
    private TableColumn<Statement, String> colDate;

    @FXML
    private TableColumn<Statement, Long> id;

    @FXML
    private ComboBox<Group> cmb_group;

    @FXML
    private ComboBox<Subject> cmb_subject;

    @FXML
    private JFXDatePicker dp_From;

    @FXML
    private JFXDatePicker dp_To;

    @FXML
    private ComboBox<String> cmb_typeСertification;

    @FXML
    private ProgressIndicator progressInd;

    private final SubjectService subjectService;

    private final GroupService groupService;

    private final StatementService statementService;

    private final SpringFXMLLoader springFXMLLoader;

    private final StudentGradesController gradesController;

    private final FxmlViewReport mark;

    private final FxmlViewMarkTeacher markTeacher;

    private final MarkControllerTeacher markControllerTeacher;

    @FXML
    private HBox hbox;

    @Autowired
    public StatementController(SubjectService subjectService, GroupService groupService,
                               StatementService statementService,
                               SpringFXMLLoader springFXMLLoader,
                               StudentGradesController gradesController,
                               @Qualifier("Mark") FxmlViewReport viewReport,
                               @Qualifier("Mark") FxmlViewMarkTeacher markTeacher,
                               MarkControllerTeacher markControllerTeacher) {
        this.subjectService = subjectService;
        this.groupService = groupService;
        this.statementService = statementService;
        this.springFXMLLoader = springFXMLLoader;
        this.gradesController = gradesController;
        this.mark = viewReport;
        this.markTeacher = markTeacher;
        this.markControllerTeacher = markControllerTeacher;
    }

    @FXML
    void SelectRow(MouseEvent event) {
        Statement statement = tableView.getSelectionModel().getSelectedItem();
        if (statement == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Ведомость не выбрана!", "Пожалуйста, выберите ведомость.");
            return;
        }

        if (event.getClickCount() == 2) {
            Stage stage = new Stage();
            gradesController.setStatement(statement);

            StageManager stageManager = new StageManager(springFXMLLoader, stage);
            if (LoginController.getUserType().equals("Admin")) {
                stageManager.switchScene(mark);
            } else {
                markControllerTeacher.setStatement(statement);
                markControllerTeacher.setGroup(statement.getGroup());
                stageManager.switchScene(markTeacher);
            }


        }
    }

    @FXML
    void handleSearch(ActionEvent event) {
        if (fieldIsEmpty()) {
            Alert alert = new Alert(CONFIRMATION);
            alert.setTitle("Поиск");
            alert.setHeaderText("Нецелесообразный поиск");
            alert.setContentText("Вы действительно хотите найти все ведомости? " +
                    "Это может занять продолжительное время.");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                search();
            }
        } else {
            search();
        }


    }

    private void search() {
        Subject subject = cmb_subject.getSelectionModel().getSelectedItem();
        String type = cmb_typeСertification.getSelectionModel().getSelectedItem();
        Group group = cmb_group.getSelectionModel().getSelectedItem();

        Task<Void> voidTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                setDisable();
                List<Statement> statementsList = null;

                if (LoginController.getUserType().equals("Admin")) {
                    statementsList = statementService.findByCriteria
                            (type, subject, group, getStartDate(), getEndDate(), "", 0L);
                }else {
                    statementsList = statementService.findByCriteria(type, subject, group, getStartDate(),
                            getEndDate(), LoginController.getUserType(), LoginController.getUserId());
                }

                statements.clear();
                statements.addAll(statementsList);
                tableView.setItems(statements);
                removeDisable();
                return null;
            }
        };
        new Thread(voidTask).start();
        resetField();
    }

    @FXML
    void handleStatementOneDay(ActionEvent event) {
        betweenDate(1);
    }

    @FXML
    void handleStatementSevenDay(ActionEvent event) {
        betweenDate(7);
    }

    @FXML
    void handleStatementThirtyDay(ActionEvent event) {
        Task<Void> voidTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                setDisable();
                betweenDate(30);
                removeDisable();
                return null;
            }
        };
        new Thread(voidTask).start();
        resetField();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        cmb_typeСertification.setItems(listTypeCertification);
        loadGroupComboBox();
        loadSubject();
        AlertMaker.initDrawer(drawer, hamburger, springFXMLLoader);
        if(!LoginController.getUserType().equals("Admin")){
            hamburger.setVisible(false);
        }
    }


    private void initCol() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_group.setCellValueFactory(new PropertyValueFactory<>("group"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void loadGroupComboBox() {
        if(LoginController.getUserType().equals("Admin")) {
            listGroups.clear();
            listGroups.addAll(groupService.findAll());
            cmb_group.setItems(listGroups);
        }else {
            listGroups.clear();
            listGroups.addAll(groupService.findGroupByTeacherId(LoginController.getUserId()));
            cmb_group.setItems(listGroups);
        }
    }

    private void loadSubject() {
        if(LoginController.getUserType().equals("Admin")) {
            listSubjects.clear();
            listSubjects.addAll(subjectService.findAll());
            cmb_subject.setItems(listSubjects);
        }else {
            listSubjects.clear();
            listSubjects.addAll(subjectService.findSubjectsByTeacherId(LoginController.getUserId()));
            cmb_subject.setItems(listSubjects);
        }
    }


    private void resetField() {
        dp_From.setValue(null);
        dp_To.setValue(null);
        cmb_subject.setValue(null);
        cmb_group.setValue(null);
        cmb_typeСertification.setValue(null);
    }

    private Date getStartDate() {
        if (dp_From.getValue() != null) {
            dateFrom = Date.from(dp_From.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return dateFrom;
    }

    private Date getEndDate() {
        if (dp_To.getValue() != null) {
            dateTo = Date.from(dp_To.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return dateTo;
    }


    private void setDisable() {
        hbox.setDisable(true);
        tableView.setDisable(true);
        hamburger.setDisable(true);
        progressInd.setVisible(true);
    }

    private void removeDisable() {
        hbox.setDisable(false);
        tableView.setDisable(false);
        hamburger.setDisable(false);
        progressInd.setVisible(false);
    }

    private boolean fieldIsEmpty() {
        boolean result = (cmb_group.getValue() == null) && (cmb_subject.getValue() == null)
                && (cmb_typeСertification.getValue() == null) && (dp_From.getValue() == null)
                && (dp_To.getValue() == null);

        return result;
    }

    private void betweenDate(int day) {
        Date currentDate = new Date();
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(day);
        Date convertDate = Date.from(thirtyDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Statement> statementsList = null;

        if(LoginController.getUserType().equals("Admin")) {
           statementsList = statementService.findByCriteria
                    (null, null, null, convertDate, currentDate, "", 0L);
        }else {
            statementsList = statementService.findByCriteria
                    (null, null, null, convertDate, currentDate,
                            LoginController.getUserType(), LoginController.getUserId());
        }

        statements.clear();
        statements.addAll(statementsList);
        tableView.setItems(statements);
    }


}
