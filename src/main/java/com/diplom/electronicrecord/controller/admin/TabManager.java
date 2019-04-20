package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.view.admin.FxmlViewMain;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class TabManager implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private Tab StudentTab;

    @FXML
    private Tab ReportTab;

    private final SpringFXMLLoader springFXMLLoader;

    private final StageManager stageManager;

    private final FxmlViewMain main;

    @Autowired
    public TabManager(SpringFXMLLoader springFXMLLoader,
                      @Lazy StageManager stageManager,
                      @Qualifier("Main") FxmlViewMain main) {
        this.springFXMLLoader = springFXMLLoader;
        this.stageManager = stageManager;
        this.main = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AlertMaker.initDrawer(drawer,hamburger,springFXMLLoader);
        handleExistKey();
    }

    private void handleExistKey(){
        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stageManager.switchScene(main);
            }
        });

    }
}
