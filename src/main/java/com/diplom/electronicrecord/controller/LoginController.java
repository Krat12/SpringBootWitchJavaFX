package com.diplom.electronicrecord.controller;

import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.model.Admin;
import com.diplom.electronicrecord.model.Teacher;
import com.diplom.electronicrecord.model.User;
import com.diplom.electronicrecord.service.UserService;
import com.diplom.electronicrecord.view.admin.FxmlViewMain;
import com.diplom.electronicrecord.view.teacher.FxmlViewGroupTeacher;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;


@Controller
public class LoginController {

    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_TEACHER = "Teacher";

    @FXML
    private JFXTextField txt_login;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private Text UserIsNull;

    private final UserService userService;

    private final FxmlViewMain admin;

    private final FxmlViewGroupTeacher teacher;

    private final StageManager stageManager;

    private static String userType;
    private static Long userId;

    @Autowired
    public LoginController(UserService userService,
                           @Qualifier("Main") FxmlViewMain admin,
                           @Lazy StageManager stageManager,
                           @Qualifier("Group") FxmlViewGroupTeacher teacher) {
        this.userService = userService;
        this.admin = admin;
        this.stageManager = stageManager;
        this.teacher = teacher;
    }

    @FXML
    void input(ActionEvent event) {
        login();
    }

    @FXML
    void LoginEnter(KeyEvent event) {
        if(event.getCode().getName().equals(KeyCode.ENTER.getName())){
            login();
        }
    }

    private void login(){
        try {
            User user = userService.authenticate(txt_login.getText(), txt_password.getText());

            if (user instanceof Admin) {
                setUserType(ROLE_ADMIN);
                setUserId(user.getId());
                stageManager.switchScene(admin);
            }

            if(user instanceof Teacher){
                setUserType(ROLE_TEACHER);
                setUserId(user.getId());
                stageManager.switchScene(teacher);
            }
        } catch (NullPointerException e) {
            userNotFound();
        }
    }

    private void userNotFound() {
        UserIsNull.setVisible(true);
        txt_login.setFocusColor(Color.web("#FF515A"));
        txt_password.setFocusColor(Color.web("#FF515A"));
        txt_password.setUnFocusColor(Color.web("#FF515A"));
        txt_login.setUnFocusColor(Color.web("#FF515A"));
        txt_login.setStyle("-fx-text-fill:#FF515A");
        txt_password.setStyle("-fx-text-fill:#FF515A");
    }

    public static String getUserType(){
        return LoginController.userType;
    }

    public static Long getUserId(){
        return LoginController.userId;
    }

    public static void setUserType(String userType) {
        LoginController.userType = userType;
    }

    public static void setUserId(Long userId) {
        LoginController.userId = userId;
    }
}
