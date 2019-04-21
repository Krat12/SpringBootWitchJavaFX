package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.view.admin.*;
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

    private FxmlViewGroup fxmlViewGroup;

    private FxmlViewTeacher fxmlViewTeacher;

    private FxmlViewSpeciality fxmlViewSpeciality;

    private FxmlViewSubject fxmlViewSubject;

    private FxmlViewStatistic fxmlViewStatistic;

    @Autowired
    public ToolBarController(@Lazy StageManager stageManager, @Qualifier("Main") FxmlViewMain main,
                             @Qualifier("Group") FxmlViewGroup fxmlViewGroup,
                             FxmlViewTeacher fxmlViewTeacher, FxmlViewSpeciality fxmlViewSpeciality,
                             FxmlViewSubject fxmlViewSubject, FxmlViewStatistic fxmlViewStatistic) {
        this.stageManager = stageManager;
        this.main = main;
        this.fxmlViewGroup = fxmlViewGroup;
        this.fxmlViewTeacher = fxmlViewTeacher;
        this.fxmlViewSpeciality = fxmlViewSpeciality;
        this.fxmlViewSubject = fxmlViewSubject;
        this.fxmlViewStatistic = fxmlViewStatistic;
    }


    @FXML
    void ShowStatistic(ActionEvent event) {
        stageManager.switchScene(fxmlViewStatistic);
    }

    @FXML
    void ShowMain(ActionEvent event) {
        stageManager.switchScene(main);
    }

    @FXML
    void showSubject(ActionEvent event) {
        stageManager.switchScene(fxmlViewSubject);
    }

    @FXML
    void ShowSpeciality(ActionEvent event) {
        stageManager.switchScene(fxmlViewSpeciality);
    }

    @FXML
    void ShowTeacher(ActionEvent event) {
        stageManager.switchScene(fxmlViewTeacher);
    }

    @FXML
    void ShowGroup(ActionEvent event) {
        stageManager.switchScene(fxmlViewGroup);
    }

}
