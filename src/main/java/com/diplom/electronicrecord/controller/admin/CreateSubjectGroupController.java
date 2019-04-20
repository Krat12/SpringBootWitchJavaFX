package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Subject;
import com.diplom.electronicrecord.model.SubjectTeacherGroup;
import com.diplom.electronicrecord.model.Teacher;
import com.diplom.electronicrecord.service.SubjectTeacherGroupService;
import com.diplom.electronicrecord.util.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Boolean.*;

@Controller
public class CreateSubjectGroupController implements Initializable {

    private ObservableList<Subject> listSubjects = FXCollections.observableArrayList();

    private ObservableList<Teacher> listTeachers = FXCollections.observableArrayList();

    private final SubjectTeacherGroupService subjectForTeacherService;

    private Boolean isEditMode = FALSE;

    private Boolean isPass = TRUE;

    private SubjectTeacherGroup subjectTeacherGroup;

    private Group group;

    @FXML
    private ComboBox<Teacher> comboBoxTeacher;

    @FXML
    private ComboBox<Subject> comboBoxSubject;


    @Autowired
    public CreateSubjectGroupController(SubjectTeacherGroupService subjectForTeacherService) {
        this.subjectForTeacherService = subjectForTeacherService;
    }

    @FXML
    void cancel(ActionEvent event) {
        AlertMaker.closeStage(event);
    }

    @FXML
    void save(ActionEvent event) {
        if (isEditMode) {
            handleOptionEdit();
            isClose(event);
        } else {
            handleOptionAdd();
            isClose(event);
        }
    }

    private void handleOptionEdit() {
        updateOrInsert();

    }

    private void handleOptionAdd() {
        updateOrInsert();
    }

    private void updateOrInsert(){
        try {

            Subject subject = comboBoxSubject.getSelectionModel().getSelectedItem();
            Teacher teacher = comboBoxTeacher.getSelectionModel().getSelectedItem();

            if (subject == null || teacher == null) {
                isPass = false;
                AlertMaker.showErrorMessage("Добавление невозможно!", "Выберите преподавателя и предмет");
                return;
            }
            subjectForTeacherService.update(getSubjectTeacherGroup(subject, teacher));
            isPass = true;

        }catch(AlreadyExistException e){
            isPass = false;
            AlertMaker.showErrorMessage("Добавление невозможно!", e.getMessage());
        }

    }

    private void isClose(ActionEvent event) {
        if (isPass) {
            AlertMaker.closeStage(event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxSubject.setItems(listSubjects);
        comboBoxTeacher.setItems(listTeachers);
    }

    void setListSubjects(ObservableList<Subject> listSubjects) {
        this.listSubjects.addAll(listSubjects);
    }

    void setListTeachers(ObservableList<Teacher> listTeachers) {
        this.listTeachers.addAll(listTeachers);
    }

     ObservableList<Subject> getListSubjects() {
        return listSubjects;
    }

     ObservableList<Teacher> getListTeachers() {
        return listTeachers;
    }

    void setGroup(Group group){
        this.group = group;
    }


     void setSubjectTeacherGroup(SubjectTeacherGroup subjectTeacherGroup, Boolean editMode) {
        this.subjectTeacherGroup = subjectTeacherGroup;
        isEditMode = editMode;
    }

    private SubjectTeacherGroup getSubjectTeacherGroup(Subject subject,Teacher teacher) {

        if (subjectTeacherGroup == null) {
            subjectTeacherGroup = new SubjectTeacherGroup();
            subjectTeacherGroup.setGroup(group);
        }
        subjectTeacherGroup.setSubject(subject);
        subjectTeacherGroup.setTeacher(teacher);

        return subjectTeacherGroup;
    }
}