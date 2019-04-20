package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.view.admin.FxmlViewMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ToolBarController {

    private final StageManager stageManager;

    private final FxmlViewMain main;

    @Autowired
    public ToolBarController(@Lazy StageManager stageManager, @Qualifier("Main") FxmlViewMain main) {
        this.stageManager = stageManager;
        this.main = main;
    }


    @FXML
    void ShowListStudent(ActionEvent event) {

    }

    @FXML
    void closeGroup(ActionEvent event) {
        stageManager.switchScene(main);
    }

    @FXML
    void handleListStudents(ActionEvent event) {

    }

    @FXML
    void loadAddGroup(ActionEvent event) {

    }

    @FXML
    void loadListGroup(ActionEvent event) {

    }

    @FXML
    void loadStudentList(ActionEvent event) {

    }

}
