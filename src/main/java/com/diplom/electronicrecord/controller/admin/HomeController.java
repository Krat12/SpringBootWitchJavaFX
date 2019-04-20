package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.view.FxmlViewManagerWindowObject;
import com.diplom.electronicrecord.view.admin.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController  {

    private final FxmlViewManagerWindowObject login;

    private final FxmlViewGroup group;

    private final FxmlViewReport statement;

    private final FxmlViewStatistic fxmlViewStatistic;

    private final FxmlViewTeacher fxmlViewTeacher;

    private final FxmlViewSubject fxmlViewSubject;

    private final FxmlViewSpeciality fxmlViewSpeciality;

    @Autowired
    public HomeController(@Qualifier("Login") FxmlViewManagerWindowObject login,
                          @Qualifier("Group") FxmlViewGroup group,
                          @Qualifier("Statement") FxmlViewReport statement,
                          @Lazy StageManager stageManager,
                          FxmlViewStatistic fxmlViewStatistic,
                          FxmlViewTeacher fxmlViewTeacher,
                          FxmlViewSubject fxmlViewSubject, FxmlViewSpeciality fxmlViewSpeciality) {
        this.login = login;
        this.group = group;
        this.statement = statement;
        this.stageManager = stageManager;
        this.fxmlViewStatistic = fxmlViewStatistic;
        this.fxmlViewTeacher = fxmlViewTeacher;
        this.fxmlViewSubject = fxmlViewSubject;
        this.fxmlViewSpeciality = fxmlViewSpeciality;
    }

    @FXML
    private FontAwesomeIconView icon_table;

    @FXML
    private FontAwesomeIconView test;

    @FXML
    private FontAwesomeIconView Idente;

    private StageManager stageManager;


    @FXML
    void handleGroupList(ActionEvent event) {
        stageManager.switchScene(group);

    }

    @FXML
    void handleOpenStatement(ActionEvent event) {
        stageManager.switchScene(statement);
    }

    @FXML
    void handleStatistic(ActionEvent event) {
        stageManager.switchScene(fxmlViewStatistic);
    }

    @FXML
    void logout(MouseEvent event) {
        stageManager.switchScene(login);
    }

    @FXML
    void handleOpenTeacherList(ActionEvent event){
        stageManager.switchScene(fxmlViewTeacher);
    }


    @FXML
    void handleOpenSubjectList(ActionEvent event) {
        stageManager.switchScene(fxmlViewSubject);
    }

    @FXML
    void handleOpenSpec(ActionEvent event) {
        stageManager.switchScene(fxmlViewSpeciality);
    }
}

