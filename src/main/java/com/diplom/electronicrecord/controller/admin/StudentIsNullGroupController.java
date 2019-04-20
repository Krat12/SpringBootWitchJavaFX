package com.diplom.electronicrecord.controller.admin;


import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Student;
import com.diplom.electronicrecord.util.AlertMaker;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class StudentIsNullGroupController  extends StudentController implements Initializable {

    private  Group group;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadStudentInTable();
    }


    @Override
    void handleStudentAddOption(ActionEvent event) {
        Student student = tableView.getSelectionModel().getSelectedItem();
        if(student == null){
            AlertMaker.showMaterialDialog(rootPane, contentPane,
                    "Студент не выбран!", "Пожалуйста, выберите Студента");
            return;
        }

        student.setGroup(group);
        studentService.saveNoCheck(student);
        handleRefresh(new ActionEvent());
    }

    @Override
    void handleStudentDeleteOption(ActionEvent event) {
       super.handleStudentDeleteOption(event);
        handleRefresh(new ActionEvent());
    }

    @Override
    protected void loadStudentInTable() {
        students.clear();
        int number = 1;
        for (Student student : studentService.findByGroupIsNullOrderBySurname()) {
            student.setNumberStudent(number);
            student.setFullName(student.toString());
            students.add(student);
            number++;
        }
        tableView.setItems(students);
    }

    @Override
    void handleRefresh(ActionEvent event) {
       loadStudentInTable();
    }

    protected void setGroup(Group group){
        this.group = group;
    }
}

