package com.diplom.electronicrecord.TestFX.pages;

import com.diplom.electronicrecord.TestFX.TestFXBase;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.text.Text;

import static com.diplom.electronicrecord.TestFX.JavaFXIds.*;


public class LoginPage {

    private final TestFXBase driver;


    public LoginPage(TestFXBase driver) {
        this.driver = driver;

    }

    public LoginPage login(String username, String password) {
        clearOutInputFields();
        enterUsername(username).enterPassword(password);
        submit();
        return this;
    }

    public LoginPage enterUsername(String username) {
        driver.clickOn(USERNAME_FIELD).write(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        driver.clickOn(PASSWORD_FIELD).write(password);
        return this;
    }

    public LoginPage submit() {
        driver.clickOn(LOGIN_BUTTON);
        return this;
    }

    public Text getLoginStatus() {
        return driver.find(LOGIN_STATUS_LABEL);
    }

    public LoginPage clearOutInputFields() {
        clearUsername().clearPassword();
        return this;
    }

    public LoginPage clearUsername() {
        JFXTextField  username = driver.find(USERNAME_FIELD);
        username.clear();
        return this;
    }

    public LoginPage clearPassword() {
        JFXPasswordField password = driver.find(PASSWORD_FIELD);
        password.clear();
        return this;
    }

}