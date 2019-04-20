package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.*;
import com.diplom.electronicrecord.service.GroupService;
import com.diplom.electronicrecord.service.SubjectTeacherGroupService;
import com.diplom.electronicrecord.service.SubjectService;
import com.diplom.electronicrecord.service.TeacherService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import com.diplom.electronicrecord.view.admin.FxmlViewGroup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

@Controller
public class SubjectGroupController implements Initializable {

    private final ObservableList<Group> listGroups = FXCollections.observableArrayList();

    private final ObservableList<SubjectTeacherGroup> subjectsForTeacher = FXCollections.observableArrayList();

    private final ObservableList<Subject> listSubjects = FXCollections.observableArrayList();

    private final ObservableList<Teacher> listTeachers = FXCollections.observableArrayList();
    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<SubjectTeacherGroup> tableView;

    @FXML
    private TableColumn<SubjectTeacherGroup, Subject> colSubject;

    @FXML
    private TableColumn<SubjectTeacherGroup, Teacher> colTeacher;

    @FXML
    private TableColumn<SubjectTeacherGroup, Integer> col_hours;

    @FXML
    private TextField txt_search;

    @FXML
    private ComboBox<Group> groupComboBox;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_addSubject;

    @FXML
    private JFXButton btn_deleteSubject;

    @FXML
    private JFXButton btn_commit;

    @FXML
    private FontAwesomeIconView lbl_commit;

    @FXML
    private JFXButton btn_rollback;

    @FXML
    private FontAwesomeIconView lbl_rollback;

    private final GroupService groupService;

    private final SubjectTeacherGroupService subjectForTeacherService;

    private final SubjectService subjectService;

    private final TeacherService teacherService;

    private final SpringFXMLLoader springFXMLLoader;

    private final FxmlViewGroup subjectGroup;

    private final CreateSubjectGroupController createSubjectGroupController;

    private Map<Integer,SubjectTeacherGroup> map = initCollectionMap();

    private Group group;

    private List<SubjectTeacherGroup> errorList;



    @Autowired
    public SubjectGroupController(GroupService groupService,
                                  SubjectTeacherGroupService subjectService,
                                  SubjectService subjectService1,
                                  TeacherService teacherService,
                                  SpringFXMLLoader springFXMLLoader,
                                  @Qualifier("CreateSubject") FxmlViewGroup subjectGroup,
                                  CreateSubjectGroupController createSubjectGroupController) {
        this.groupService = groupService;
        this.subjectForTeacherService = subjectService;
        this.subjectService = subjectService1;
        this.teacherService = teacherService;
        this.springFXMLLoader = springFXMLLoader;
        this.subjectGroup = subjectGroup;
        this.createSubjectGroupController = createSubjectGroupController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadGroupComboBox();
        initCol();
        loadDataTable();
        loadSubject();
        loadTeacher();
        comboBoxSubject();
        comboBoxTeacher();
        changeTextField();
        settingsTable();
    }

    @FXML
    void handleCommit(ActionEvent event) {

        if (map.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Нет данных для обновления",
                    "Пожалуйста, измените данные и попробуйте еще раз");
            return;
        }

        errorList = new ArrayList<>();

        for (Map.Entry<Integer,SubjectTeacherGroup> entry : map.entrySet()) {
            try {
                subjectForTeacherService.update(entry.getValue());
            } catch (AlreadyExistException e) {
                errorList.add(entry.getValue());
            }
        }
        outputErrorList();
        handleRefresh(new ActionEvent());
    }

    private void outputErrorList() {
        if (!errorList.isEmpty()) {
            AlertMaker.showErrorMessage("Ошибка!",
                    "Было обнаружено совподение (" + errorList.size() + " записи)");
        }
    }

    @FXML
    void handleRollback(ActionEvent event) {
        if (map.isEmpty()) {
            return;
        }
        handleRefresh(new ActionEvent());

    }

    @FXML
    void handleCopySubjectGroup(ActionEvent event) {
        if (groupComboBox.getSelectionModel().getSelectedItem() == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, "Группа не выбрана!", "Пожалуйста, выберите группу");
            return;
        }
        subjectForTeacherService.copyByInsert(group.getId(), groupComboBox.getSelectionModel().getSelectedItem().getId());
        handleRefresh(new ActionEvent());
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        AlertMaker.resetColorCommit(lbl_commit, lbl_rollback, btn_commit, btn_rollback);
        map.clear();
        resetDisable();
        loadDataTable();
    }

    @FXML
    void handleSubjectAdd(ActionEvent event) {
        createSubjectGroupController.setGroup(group);
        initUI();
    }

    private void initUI() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        StageManager stageManager = new StageManager(springFXMLLoader, stage);
        if (createSubjectGroupController.getListSubjects().isEmpty()) {
            createSubjectGroupController.setListSubjects(listSubjects);
        }
        if (createSubjectGroupController.getListTeachers().isEmpty()) {
            createSubjectGroupController.setListTeachers(listTeachers);
        }
        stageManager.switchScene(subjectGroup);
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }

    @FXML
    void handleSubjectDeleteOption(ActionEvent event) {
        SubjectTeacherGroup subjectTeacherGroup = tableView.getSelectionModel().getSelectedItem();

        if (subjectTeacherGroup == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Предмет не выбран!", "Пожалуйста, выберите Предмет");
            return;
        }
        Alert alert = new Alert(CONFIRMATION);
        alert.setTitle("Удаление");
        alert.setHeaderText("Вы действительно хотите удалить?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            subjectForTeacherService.delete(subjectTeacherGroup);
            handleRefresh(new ActionEvent());
        }
    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void exportCSV(ActionEvent event) throws IOException {
            String path = AlertMaker.initFileChooserExport("CSV (разделители - точка с запятой) (*.csv)", "*.csv");
            if (!path.isEmpty()) {
                HandlerCSVUtil.exportFile(getValuesCSV(), getHeaderCSV(), ';', path);
            }
    }

    private List<String> getHeaderCSV() {
        List<String> strings = new ArrayList<>();
        strings.add("Предмет");
        strings.add("Преподаватель");
        strings.add("Количество часов");
        return strings;
    }

    private List<String[]> getValuesCSV() {
        List<String[]> strings = new ArrayList<>();
        for (SubjectTeacherGroup obj : subjectsForTeacher) {
            String[] line = new String[3];
            line[0] = obj.getSubject().getNameSubject();
            line[1] = obj.getTeacher().getSurname() + " "+obj.getTeacher().getName()+" "
                    +obj.getTeacher().getPatronymic();
            line[2] = String.valueOf(obj.getHours());
            strings.add(line);
        }
        return strings;
    }

    @FXML
    void handleImportCSV(ActionEvent event) {
        String patch = AlertMaker.initFileChooserImport("CSV (разделители - точка с запятой) (*.csv)", "*.csv");

        if (!patch.isEmpty()) {
            readerCSV(patch);
        }
    }

    @FXML
    void handleSubjectEditOption(ActionEvent event) {
        SubjectTeacherGroup subjectTeacherGroup = tableView.getSelectionModel().getSelectedItem();
        if(subjectTeacherGroup == null){
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Предмет не выбран!", "Пожалуйста, выберите Предмет");
            return;
        }
        createSubjectGroupController.setSubjectTeacherGroup(subjectTeacherGroup, true);
        initUI();
    }


    private void loadDataTable() {
        subjectsForTeacher.clear();
        subjectsForTeacher.addAll(subjectForTeacherService.findAllTeacherAndSubjectByGroupId(group.getId()));
        tableView.setItems(subjectsForTeacher);
    }

    private void loadGroupComboBox() {
        listGroups.clear();
        listGroups.addAll(groupService.findAll());
        groupComboBox.setItems(listGroups);
    }

    private void loadSubject() {
        listSubjects.clear();
        listSubjects.addAll(subjectService.findAll());
    }

    private void loadTeacher() {
        listTeachers.clear();
        listTeachers.addAll(teacherService.findAll());
    }

    private void initCol() {
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        col_hours.setCellValueFactory(new PropertyValueFactory<>("hours"));
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private void comboBoxSubject() {
        tableView.setEditable(true);
        colSubject.setCellValueFactory((TableColumn.CellDataFeatures<SubjectTeacherGroup, Subject> param) -> {

            SubjectTeacherGroup subjectModel = param.getValue();
            Subject subject = subjectModel.getSubject();
            return new SimpleObjectProperty<>(subject);
        });

        colSubject.setCellFactory(ComboBoxTableCell.forTableColumn(listSubjects));

        colSubject.setOnEditCommit((TableColumn.CellEditEvent<SubjectTeacherGroup, Subject> event) -> {

            Subject oldSubject = event.getOldValue();

            Subject newSubject = event.getNewValue();

            if (!newSubject.equals(oldSubject)) {

                commitTable(event, newSubject);
            }
        });
    }

    private void comboBoxTeacher() {
        tableView.setEditable(true);
        colTeacher.setCellValueFactory((TableColumn.CellDataFeatures<SubjectTeacherGroup, Teacher> param) -> {

            SubjectTeacherGroup teacherModel = param.getValue();
            Teacher teacher = teacherModel.getTeacher();
            return new SimpleObjectProperty<>(teacher);
        });

        colTeacher.setCellFactory(ComboBoxTableCell.forTableColumn(listTeachers));

        colTeacher.setOnEditCommit((TableColumn.CellEditEvent<SubjectTeacherGroup, Teacher> event) -> {

            Teacher oldTeacher = event.getOldValue();

            Teacher newTeacher = event.getNewValue();

            if (!newTeacher.equals(oldTeacher)) {
                commitTable(event, newTeacher);
            }
        });
    }

    private void commitTable(TableColumn.CellEditEvent<SubjectTeacherGroup, ?> event, Object obj) {

        TablePosition<SubjectTeacherGroup, ?> position = event.getTablePosition();
        int row = position.getRow();
        SubjectTeacherGroup subjectTeacherGroup = event.getTableView().getItems().get(row);
        if (obj instanceof Teacher) {
            subjectTeacherGroup.setTeacher((Teacher) obj);
        }
        if (obj instanceof Subject) {
            subjectTeacherGroup.setSubject((Subject) obj);
        }
        map.put(row,subjectTeacherGroup);

        AlertMaker.setColorCommitAndRollBack(lbl_commit, lbl_rollback, btn_commit, btn_rollback);
        setDisable();

    }


    private Map<Integer,SubjectTeacherGroup> initCollectionMap() {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    private void changeTextField() {
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            subjectsForTeacher.clear();
            subjectsForTeacher.addAll(subjectForTeacherService.findBySubjectNameAndFullName
                    (group.getId(), newValue));
        });
    }

    private void readerCSV(String patch) {
        try {
            List<SubjectTeacherGroup> list = subjectForTeacherService.readerCSV(patch, group);
            subjectsForTeacher.addAll(list);
            for (int index = 0; index < subjectsForTeacher.size() ; index++) {
                if(subjectsForTeacher.get(index).getId() == 0){
                    map.put(index,subjectsForTeacher.get(index));
                }
            }

            setDisable();
            AlertMaker.setColorCommitAndRollBack(lbl_commit, lbl_rollback, btn_commit, btn_rollback);
        } catch (IOException e) {
            AlertMaker.showErrorMessage("Ошибка при чтении файла", "Возможно файл поврежден или не тот формат");

        } catch (NullPointerException n) {
            AlertMaker.showErrorMessage("Ошибка при чтении файла",n.getMessage());

        } catch (Exception e) {
            AlertMaker.showErrorMessage("Ошибка при чтении файла", "Возможно несовподение типов или пустой");

        }

    }
    private void setDisable(){
        btn_addSubject.setDisable(true);
        btn_edit.setDisable(true);
        btn_deleteSubject.setDisable(true);
    }
    private void resetDisable(){
        btn_addSubject.setDisable(false);
        btn_edit.setDisable(false);
        btn_deleteSubject.setDisable(false);
    }

    private void settingsTable() {

        tableView.setEditable(true);
        col_hours.setCellFactory(integerCell(value -> value >= 0));
        col_hours.setOnEditCommit((event) -> {
            Integer value = event.getNewValue();
            if (value != null) {

                TablePosition<SubjectTeacherGroup, Integer> pos = event.getTablePosition();
                int row = pos.getRow();
               SubjectTeacherGroup subjectTeacherGroup = event.getTableView().getItems().get(row);

                subjectTeacherGroup.setHours(value);
                map.put(row,subjectTeacherGroup);
                AlertMaker.setColorCommitAndRollBack(lbl_commit, lbl_rollback, btn_commit, btn_rollback);
            }
        });
    }

    private  <T> Callback<TableColumn<T, Integer>, TableCell<T, Integer>> integerCell(
            Predicate<Integer> validator) {
        return TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object == null) {
                    return null;
                }
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                try {
                    int value = Integer.parseInt(string);
                    if (validator.test(value)) {
                        return value;
                    } else {
                        AlertMaker.showMaterialDialog(rootPane, contentPane,  "Неверный формат!",
                                "Количество часов не может быть меньше нуля!");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    AlertMaker.showMaterialDialog(rootPane, contentPane,  "Неверный формат!",
                            "Введите целое положительное число без разделителей");
                    return null;
                }
            }
        });
    }
}
