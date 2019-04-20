package com.diplom.electronicrecord.controller.teacher;

import com.diplom.electronicrecord.model.Diplom;
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
public class EditDiplomController implements Initializable {

    @FXML
    private JFXTextField txt_thesis;

    @FXML
    private Label lbl_error;

    private final MarkService markService;

    private Diplom diplom;

    public void setDiplom(Diplom diplom) {
        this.diplom = diplom;
    }

    @Autowired
    public EditDiplomController(MarkService markService) {
        this.markService = markService;
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
        diplom.setThesis(txt_thesis.getText());
        markService.update(diplom);
        AlertMaker.closeStage(event);
    }

    private void error() {
        lbl_error.setVisible(true);
        txt_thesis.setUnFocusColor(Color.web("#FF515A"));
        txt_thesis.setStyle("-fx-text-fill:#FF515A");
    }

    private boolean checkField() {
        if(txt_thesis.getText().trim().isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txt_thesis.setText(diplom.getThesis());
    }
}
