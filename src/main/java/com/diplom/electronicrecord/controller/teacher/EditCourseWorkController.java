package com.diplom.electronicrecord.controller.teacher;

import com.diplom.electronicrecord.model.CourseWork;
import com.diplom.electronicrecord.service.MarkService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;


@Controller
public class EditCourseWorkController implements Initializable {

    @FXML
    private JFXTextField txt_FullNameBoss;

    @FXML
    private JFXTextField txt_plasePractic;

    @FXML
    private Label lbl_error;

    private final MarkService markService;


    private  CourseWork courseWork;

    @Autowired
    public EditCourseWorkController(MarkService markService) {
        this.markService = markService;
    }


    void setCourseWork(CourseWork courseWork) {
        this.courseWork = courseWork;
    }

    @FXML
    void cancel(ActionEvent event) {
        AlertMaker.closeStage(event);
    }

    @FXML
    void save(ActionEvent event) {
        if(checkField()){
            error();
            return;
        }
        courseWork.setFullNameBoss(txt_FullNameBoss.getText());
        courseWork.setPlacePractice(txt_plasePractic.getText());
        markService.update(courseWork);
        AlertMaker.closeStage(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txt_plasePractic.setText(courseWork.getPlacePractice());
        txt_FullNameBoss.setText(courseWork.getFullNameBoss());
    }

    private boolean checkField(){
        if(txt_plasePractic.getText().trim().isEmpty()){
            return true;
        }
        if(txt_FullNameBoss.getText().trim().isEmpty()){
            return true;
        }
        return false;
    }

    private void error() {
        lbl_error.setVisible(true);
        txt_FullNameBoss.setFocusColor(Color.web("#FF515A"));
        txt_plasePractic.setFocusColor(Color.web("#FF515A"));
        txt_FullNameBoss.setUnFocusColor(Color.web("#FF515A"));
        txt_FullNameBoss.setStyle("-fx-text-fill:#FF515A");
        txt_plasePractic.setStyle("-fx-text-fill:#FF515A");
    }
}
