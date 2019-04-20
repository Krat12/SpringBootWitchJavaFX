package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.controller.LoginController;
import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.service.GroupService;
import com.diplom.electronicrecord.service.SpecialityService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.view.admin.FxmlViewCreate;
import com.diplom.electronicrecord.view.admin.FxmlViewGroup;
import com.diplom.electronicrecord.view.admin.FxmlViewMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.*;


@Controller
public class GroupController implements Initializable {

    private final ObservableList<Group> listGroups = FXCollections.observableArrayList();

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TableView<Group> tableView;

    @FXML
    private TableColumn<Group, String> groupCol;

    @FXML
    private TableColumn<Group, Integer> yearCol;

    @FXML
    private TableColumn<Group, Integer> id;

    @FXML
    private MenuItem con_edit;

    @FXML
    private MenuItem con_deleteGroup;

    @FXML
    private MenuItem con_addGroup;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_addGroup;

    @FXML
    private JFXButton btn_deleteGroup;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private ProgressIndicator progressInd;

    @FXML
    private JFXButton btn_graduates;

    private final StageManager stageManager;

    private final GroupService groupService;

    private SpringFXMLLoader springFXMLLoader;

    private final FxmlViewGroup student;

    private final FxmlViewCreate createOrEditGroup;

    private final FxmlViewMain mainMenu;

    private final StudentController studentController;

    private final CreateGroupController groupController;

    private final SpecialityService specialityService;

    private final FxmlViewGroup subject;

    private final SubjectGroupController subjectController;

    @Autowired
    public GroupController(@Lazy StageManager stageManager,
                           GroupService groupService,
                           @Qualifier("Student") FxmlViewGroup student,
                           @Qualifier("CreateGroup") FxmlViewCreate createOrEditGroup,
                           @Qualifier("Main") FxmlViewMain mainMenu,
                           StudentController studentController,
                           CreateGroupController groupController, SpecialityService specialityService,
                           @Qualifier("Subject") FxmlViewGroup subject,
                           SubjectGroupController subjectController) {
        this.stageManager = stageManager;
        this.groupService = groupService;
        this.student = student;
        this.createOrEditGroup = createOrEditGroup;
        this.mainMenu = mainMenu;
        this.studentController = studentController;
        this.groupController = groupController;
        this.specialityService = specialityService;
        this.subject = subject;
        this.subjectController = subjectController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadGroupInTable();
        checkTeacher();
        changeTextField();
        AlertMaker.initDrawer(drawer,hamburger,springFXMLLoader);
    }

    private void loadGroupInTable() {
        listGroups.clear();
        listGroups.addAll(groupService.findAll());
        tableView.setItems(listGroups);
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
    void closeStage(ActionEvent event) {
        stageManager.switchScene(mainMenu);
    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }
    @FXML
    void handleGraduates(ActionEvent event) throws AlreadyExistException {
        Group group = checkSelectGroup();
        if(group != null) {
            group.setStatus("выпустились");
            group.setSpeciality(specialityService.findSpecialityByGroup(group.getGroupName()));
            groupService.update(group);
            handleRefresh(new ActionEvent());
        }
    }

    @FXML
    void handleGroupAdd(ActionEvent event) {
        initUI();
    }

    private void initUI() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        StageManager stageManager = new StageManager(springFXMLLoader, stage);
        stageManager.switchScene(createOrEditGroup);
        stage.setOnHiding((e) -> {
            groupController.setInEditMode(false);
            handleRefresh(new ActionEvent());
        });
    }

    @FXML
    void handleGroupDeleteOption(ActionEvent event) {
        Group group = checkSelectGroup();
        if(group != null) {
            Alert alert = new Alert(CONFIRMATION);
            alert.setTitle("Удаление группы");
            alert.setHeaderText("Удалить группу "+group.getGroupName()+" ?");
            alert.setContentText("Вы действительно хотите удалить группу?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                groupService.delete(group);
            }
        }

    }

    @FXML
    void handleGroupEditOption(ActionEvent event) {
        Group group = checkSelectGroup();
        if(group != null) {
            groupController.setGroup(group);
            initUI();
        }
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadGroupInTable();
    }

    @FXML
    void handleStudentList(ActionEvent event) {
        Group group = checkSelectGroup();
        if(group != null) {
            studentController.setGroup(group);
            loadStage(student);
        }

    }

    @FXML
    void handleSubjectList(ActionEvent event) {
        Group group = checkSelectGroup();
        if(group != null) {
            subjectController.setGroup(group);
            loadStage(subject);
        }
    }

    private void loadStage(FxmlViewGroup fxmlViewManager){
        StageManager stageManager = new StageManager(springFXMLLoader, new Stage());
        stageManager.switchScene(fxmlViewManager);
    }

    private Group checkSelectGroup(){
        Group group = tableView.getSelectionModel().getSelectedItem();

        if (group == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Группа не выбрана!", "Пожалуйста, выберите группу");
            return null;
        }
       return group;
    }

    private void changeTextField() {
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            listGroups.clear();
            listGroups.addAll(groupService.findByGroupNameStartingWith(newValue));
        });
    }

    private void initCol() {
        groupCol.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
    }

    private void checkTeacher() {
        if (LoginController.getUserType().equals("Teacher")) {
            btn_addGroup.setVisible(false);
            btn_deleteGroup.setVisible(false);
            btn_edit.setVisible(false);
            con_addGroup.setVisible(false);
            con_edit.setVisible(false);
            con_deleteGroup.setVisible(false);
        }
    }


    @Autowired
    public void setSpringFXMLLoader(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }
}
