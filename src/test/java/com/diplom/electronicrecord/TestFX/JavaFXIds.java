/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diplom.electronicrecord.TestFX;

/**
 * Class holding Constants in order to improve maintenance and centralize all
 * Java fx:id's.
 *
 */
public class JavaFXIds {

    //Login Window
    public final static String USERNAME_FIELD = "#txt_login";
    public final static String PASSWORD_FIELD = "#txt_password";
    public final static String LOGIN_BUTTON = "#loginButton";
    public final static String LOGIN_STATUS_LABEL = "#UserIsNull";

    //Home Window
    public final static String HOME_BUTTON_LIST_GROUP = "#ListGroup";
    public final static String HOME_BUTTON_LIST_REPORT = "#ListReport";

    //TestAdmin
    public final static String VALID_USERNAME = "admin";
    public final static String VALID_PASSWORD = "1234";

    //Alert
    public final static String ALERT_DIALOG = "#header";
    public final static String ALERT_BUTTON_DIALOG = "#ButtonDialog";

    //GroupList editGroup
    public final static String GROUP_LIST_EDIT= "#editGroup";

    private JavaFXIds(){}; //not suppose to instantiate
}
