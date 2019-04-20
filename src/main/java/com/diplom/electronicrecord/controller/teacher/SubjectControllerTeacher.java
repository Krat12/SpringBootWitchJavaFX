package com.diplom.electronicrecord.controller.teacher;

import com.diplom.electronicrecord.controller.LoginController;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Subject;
import com.diplom.electronicrecord.model.SubjectTeacherGroup;
import com.diplom.electronicrecord.service.SubjectTeacherGroupService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class SubjectControllerTeacher implements Initializable {

    private final ObservableList<SubjectTeacherGroup> subjectsForTeacher = FXCollections.observableArrayList();

    @FXML
    private TableView<SubjectTeacherGroup> tableView;

    @FXML
    private TableColumn<SubjectTeacherGroup, Group> colGroup;

    @FXML
    private TableColumn<SubjectTeacherGroup, Subject> colSubject;

    @FXML
    private TableColumn<SubjectTeacherGroup, Long> id;

    @FXML
    private TableColumn<SubjectTeacherGroup, Integer> col_hours;

    private final SubjectTeacherGroupService subjectTeacherGroupService;

    @Autowired
    public SubjectControllerTeacher(SubjectTeacherGroupService subjectTeacherGroupService) {
        this.subjectTeacherGroupService = subjectTeacherGroupService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadDataInTable();
    }

    private void loadDataInTable(){
        subjectsForTeacher.clear();
        subjectsForTeacher.addAll(subjectTeacherGroupService.findAllGroupAndSubjectByTeacherId(LoginController.getUserId()));
        tableView.setItems(subjectsForTeacher);
    }

    private void initCol() {
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colGroup.setCellValueFactory(new PropertyValueFactory<>("group"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_hours.setCellValueFactory(new PropertyValueFactory<>("hours"));

    }
}
