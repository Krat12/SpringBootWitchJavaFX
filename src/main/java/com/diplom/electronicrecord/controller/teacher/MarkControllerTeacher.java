package com.diplom.electronicrecord.controller.teacher;

import com.diplom.electronicrecord.controller.admin.StudentGradesController;
import com.diplom.electronicrecord.model.*;
import com.diplom.electronicrecord.util.AlertMaker;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class MarkControllerTeacher extends StudentGradesController implements Initializable {

   private ObservableList<String> pass = FXCollections.observableArrayList();

    private static final String[] STRING_MARKS = {"Зачет", "Незачет"};

    private static final String[] NUMBER_MARKS = {"5", "4", "3", "2", "1", "н/a"};
    
    private int row;

    @FXML
    private JFXButton btn_commit;

    @FXML
    private FontAwesomeIconView commit;

    @FXML
    private JFXButton btn_rollback;

    @FXML
    private FontAwesomeIconView rollback;

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane contentPane;

    private Map<Integer, Marks> map = initCollectionMap();

    private Group group;

    private Boolean isReady;



    @FXML
    void handleMouseClicked(MouseEvent event) {
        Marks model = tableView.getSelectionModel().getSelectedItem();
        if (model != null) {
            if (getStatement().getType().equals("Зачет")) {
                pass.clear();
                pass.addAll(STRING_MARKS);
            } else {
                pass.clear();
                pass.addAll(NUMBER_MARKS);
            }
        }
    }


    @FXML
    void handleRefresh(ActionEvent event) {
        AlertMaker.resetColorCommit(commit, rollback, btn_commit, btn_rollback);
        isReady = Boolean.FALSE;
        loadDataTable();
    }

    @FXML
    void handleStudentAddOption(ActionEvent event) {
        markService.insertByGroupAndStatement(getStatement(),getGroup().getId());
        handleRefresh(new ActionEvent());
    }

    @FXML
    void handleStudentCommit(ActionEvent event) {
        if(isReady){
            for (Map.Entry<Integer, Marks> mark: map.entrySet()) {
                Marks marks = mark.getValue();
                marks.setMark(getMarkInt(marks.getMarkString()));
                markService.update(marks);
            }
            handleRefresh(new ActionEvent());
        }
    }


    @FXML
    void handleStudentRolback(ActionEvent event) {
        if(isReady){
            map.clear();
            handleRefresh(new ActionEvent());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadDataTable();
        settingColumnMarkPassed();
    }

    @Override
    public void setStatement(Statement statement) {
        super.setStatement(statement);
    }

    private void settingColumnMarkPassed() {
        tableView.setEditable(true);
        comboBoxMark();
        settingColumnThesis();
        settingColumnFullNameBoss();
        settingColumnPlacePractice();

    }

    @FXML
    void handleKeyAchievement(KeyEvent event) {
        Marks marks = tableView.getSelectionModel().getSelectedItem();
        if (marks != null) {

            if (getStatement().getType().equals("Зачет")) {

                setMarkPussedToFail(event.getCode().getName(), marks);

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

    private void setMarkPussedToFail(String key, Marks marks) {
        if (key.equals("1")) {
            marks.setMarkString("Зачет");
            updateMark(marks);
        }

        if (key.equals("2")) {
            marks.setMarkString("Незачет");
            updateMark(marks);
        }
    }

    private void comboBoxMark() {
        colMark.setCellFactory(ComboBoxTableCell.forTableColumn(pass));

        colMark.setOnEditCommit((TableColumn.CellEditEvent<Marks, String> event) -> {
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

    private void settingColumnThesis() {
        thesis.setCellFactory(TextFieldTableCell.<Marks>forTableColumn());
        thesis.setOnEditCommit((TableColumn.CellEditEvent<Marks, String> event) -> {

            String thesis = event.getNewValue();

            if (!thesis.trim().isEmpty()) {

                TablePosition<Marks, String> pos = event.getTablePosition();
                int row = pos.getRow();
                Diplom diplom = (Diplom) event.getTableView().getItems().get(row);

                diplom.setThesis(thesis);
                map.put(row,diplom);
                isReady = Boolean.TRUE;
                AlertMaker.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
            }

        });
    }

    private void settingColumnFullNameBoss() {
        colFullNameBoss.setCellFactory(TextFieldTableCell.<Marks>forTableColumn());
        colFullNameBoss.setOnEditCommit((TableColumn.CellEditEvent<Marks, String> event) -> {

            String fullNameBoss = event.getNewValue();

            if (!fullNameBoss.trim().isEmpty()) {

                CourseWork courseWork = editCourseWork(event);
                courseWork.setFullNameBoss(fullNameBoss);
                map.put(row,courseWork);
            }

        });
    }

    private void settingColumnPlacePractice() {
        placePractice.setCellFactory(TextFieldTableCell.<Marks>forTableColumn());
        placePractice.setOnEditCommit((TableColumn.CellEditEvent<Marks, String> event) -> {

            String place = event.getNewValue();

            if (!place.trim().isEmpty()) {

                CourseWork courseWork = editCourseWork(event);
                courseWork.setPlacePractice(place);
                map.put(row,courseWork);
               
            }

        });
    }
    
    private CourseWork editCourseWork(TableColumn.CellEditEvent<Marks, String> event){
        TablePosition<Marks, String> pos = event.getTablePosition();
        row = pos.getRow();
        CourseWork courseWork = (CourseWork) event.getTableView().getItems().get(row); 
        AlertMaker.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
        isReady = Boolean.TRUE;
        return courseWork;
    }

    private void updateMark(Marks marks) {
        int index = tableView.getSelectionModel().getSelectedIndex();
        map.put(index,marks);
        AlertMaker.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
        isReady = Boolean.TRUE;
        marksObservableList.set(index, marks);
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

    private Map<Integer, Marks> initCollectionMap() {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
