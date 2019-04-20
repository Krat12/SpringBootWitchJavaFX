package com.diplom.electronicrecord.controller.teacher;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.controller.LoginController;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Student;
import com.diplom.electronicrecord.service.GroupService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.view.admin.FxmlViewReport;
import com.diplom.electronicrecord.view.teacher.FxmlViewGroupTeacher;
import com.diplom.electronicrecord.view.teacher.FxmlViewMarkTeacher;
import com.diplom.electronicrecord.view.teacher.FxmlViewSubjectTeacher;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class GroupControllerTeacher implements Initializable {

    private final ObservableList<Group> listGroups = FXCollections.observableArrayList();

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<Group> tableView;

    @FXML
    private TableColumn<Group, String> groupCol;

    @FXML
    private TableColumn<Group, Integer> yearCol;

    @FXML
    private TableColumn<Group, Long> id;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TextField txt_search;

    private final GroupService groupService;

    private final SpringFXMLLoader springFXMLLoader;

    private final FxmlViewGroupTeacher student;

    private final StudentControllerTeacher studentController;

    private final FxmlViewReport fxmlViewReport;

    private final FxmlViewSubjectTeacher fxmlViewSubjectTeacher;

    private final FxmlViewMarkTeacher fxmlViewMarkTeacher;


    @Autowired
    public GroupControllerTeacher(GroupService groupService, SpringFXMLLoader springFXMLLoader,
                                  @Qualifier("Student") FxmlViewGroupTeacher student,
                                  StudentControllerTeacher studentController,
                                  @Qualifier("Statement") FxmlViewReport fxmlViewReport,
                                  FxmlViewSubjectTeacher fxmlViewSubjectTeacher,
                                  @Qualifier("EditMark") FxmlViewMarkTeacher fxmlViewMarkTeacher) {
        this.groupService = groupService;
        this.springFXMLLoader = springFXMLLoader;
        this.student = student;
        this.studentController = studentController;

        this.fxmlViewReport = fxmlViewReport;
        this.fxmlViewSubjectTeacher = fxmlViewSubjectTeacher;
        this.fxmlViewMarkTeacher = fxmlViewMarkTeacher;
    }

    @FXML
    void SelectGroup(MouseEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            if (event.getClickCount() == 2) {
                handleStudentList(new ActionEvent());
            }
        }
    }

    @FXML
    void handleOpenMark(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        StageManager stageManager = new StageManager(springFXMLLoader,stage);
        stageManager.switchScene(fxmlViewMarkTeacher);
    }

    @FXML
    void handleOpenProfile(ActionEvent event) {

    }

    @FXML
    void handleOpenStatement(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        StageManager stageManager = new StageManager(springFXMLLoader,stage);
        stageManager.switchScene(fxmlViewReport);
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadDataInTable();
    }

    @FXML
    void handleStudentList(ActionEvent event) {
        Group group = tableView.getSelectionModel().getSelectedItem();
        if(group == null){
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Группа не выбрана!", "Пожалуйста, выберите группу");
            return;
        }
        studentController.setGroup(group);
        loadStage(student);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadDataInTable();
        changeTextField();

    }

    private void initCol() {
        groupCol.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
    }

    private void loadDataInTable(){
        listGroups.clear();
        listGroups.addAll(groupService.findGroupByTeacherId(LoginController.getUserId()));
        tableView.setItems(listGroups);
    }

    @FXML
    void handleSubjectTeacher(ActionEvent event) {
        StageManager stageManager = new StageManager(springFXMLLoader, new Stage());
        stageManager.switchScene(fxmlViewSubjectTeacher);
    }
    private void loadStage(FxmlViewGroupTeacher fxmlViewManager){
        StageManager stageManager = new StageManager(springFXMLLoader, new Stage());
        stageManager.switchScene(fxmlViewManager);
    }

    private void changeTextField() {
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            listGroups.clear();
            listGroups.addAll(groupService.findGroupByTeacherIdAndStartingWith(LoginController.getUserId(),newValue));
            tableView.setItems(listGroups);
        });
    }

}

