package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Student;
import com.diplom.electronicrecord.service.GroupService;
import com.diplom.electronicrecord.service.StudentService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.util.ValidationUtil;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.ValidationException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class CreateStudentController implements Initializable {

    private Boolean isInEditMode = Boolean.FALSE;

    private Student studentForUpdate;

    private Boolean isEmpty = Boolean.FALSE;

    private final ObservableList<Group> list = FXCollections.observableArrayList();

    private boolean isPass = false;

    @FXML
    private JFXTextField txt_surname;

    @FXML
    private JFXTextField txt_name;

    @FXML
    private JFXTextField txt_patronymic;

    @FXML
    private JFXTextField txt_numberRecord;

    @FXML
    private JFXTextField txt_login;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private ComboBox<Group> groupComboBox;

    @FXML
    private FontAwesomeIconView lbl_surname;

    @FXML
    private FontAwesomeIconView lbl_name;

    @FXML
    private FontAwesomeIconView lbl_midleName;

    @FXML
    private FontAwesomeIconView lbl_NumberRecord;

    @FXML
    private FontAwesomeIconView lbl_login;

    @FXML
    private FontAwesomeIconView lbl_password;

    @FXML
    private Text txt_error;

    private final GroupService groupService;

    private final StudentService studentService;

    private Group group;

    @Autowired
    public CreateStudentController(GroupService groupService, StudentService studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataInComboBox();
        mappingUI();
        ValidationUtil.textFieldIsDigit(txt_numberRecord);
    }

    @FXML
    void cancel(ActionEvent event) {
        AlertMaker.closeStage(event);
    }

    @FXML
    void handleAutoGenerationLogin(ActionEvent event) {
        txt_login.setText(generateLogin());
    }

    @FXML
    void save(ActionEvent event) {
        if(isInEditMode){
            handleEditOperation();
            isClose(event);
        }else {
            handleAddOperation();
            isClose(event);
        }
    }


    private void isClose(ActionEvent event){
        if(isPass){
            AlertMaker.closeStage(event);
        }
    }

    private void handleEditOperation(){
        if(fieldIsNull()){
            return;
        }
        try {
            Group group =  groupComboBox.getSelectionModel().getSelectedItem();
            studentForUpdate = getStudentForUpdate();
            studentForUpdate.setGroup(group);
            studentService.update(studentForUpdate);
            isPass = true;
        } catch (AlreadyExistException | ValidationException e) {
            AlertMaker.showErrorMessage("Ошибка при добавлении",e.getMessage());
            isPass = false;
        }
    }

    private void handleAddOperation(){
        if(fieldIsNull()){
            return;
        }
        try {
            studentService.save(getStudentForUpdate());
            isPass = true;
        } catch (AlreadyExistException | ValidationException e) {
            AlertMaker.showErrorMessage("Ошибка при добавлении",e.getMessage());
            isPass = false;
        }

    }

    private void loadDataInComboBox() {
        list.clear();
        list.addAll(groupService.findAll());
        groupComboBox.setItems(list);
    }

    void setStudentForUpdate(Student studentForUpdate) {
        this.studentForUpdate = studentForUpdate;
        isInEditMode = Boolean.TRUE;
    }

    private void mappingUI() {

       if(isInEditMode) {
           txt_name.setText(studentForUpdate.getName());
           txt_surname.setText(studentForUpdate.getSurname());
           txt_patronymic.setText(studentForUpdate.getPatronymic());
           txt_login.setText(studentForUpdate.getLogin());
           txt_password.setText(studentForUpdate.getPassword());
           txt_numberRecord.setText(String.valueOf(studentForUpdate.getNumberBook()));
           groupComboBox.setVisible(true);
           groupComboBox.getSelectionModel().select(studentForUpdate.getGroup());
       }

    }

    private String generateLogin() {
        return new Random().ints(5, 65, 90).
                mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
    }

    void setGroup(Group group){
        this.group = group;
    }

    private Student getStudentForUpdate(){

       Student student = new Student();
       if(studentForUpdate != null){
           student.setId(studentForUpdate.getId());
       }
        student.setName(txt_name.getText());
        student.setSurname(txt_surname.getText());
        student.setPatronymic(txt_patronymic.getText());
        student.setLogin(txt_login.getText());
        student.setPassword(txt_password.getText());
        student.setNumberBook(Integer.valueOf(txt_numberRecord.getText()));
        student.setGroup(group);
        return student;
    }
    private boolean fieldIsNull() {
        isEmpty = Boolean.FALSE;

        if (txt_surname.getText().trim().isEmpty()) {
            nullErrorUI(txt_surname,txt_error,lbl_surname);
            isEmpty = Boolean.TRUE;
        }
        if (txt_name.getText().trim().isEmpty()) {
            nullErrorUI(txt_name,txt_error,lbl_name);
            isEmpty = Boolean.TRUE;
        }
        if (txt_patronymic.getText().trim().isEmpty()) {
            nullErrorUI(txt_patronymic,txt_error,lbl_midleName);
            isEmpty = Boolean.TRUE;
        }
        if (txt_numberRecord.getText().trim().isEmpty()) {
            nullErrorUI(txt_numberRecord,txt_error,lbl_NumberRecord);
            isEmpty = Boolean.TRUE;
        }
        if (txt_login.getText().trim().isEmpty()) {
            nullErrorUI(txt_login,txt_error,lbl_login);
            isEmpty = Boolean.TRUE;
        }
        if (txt_password.getText().trim().isEmpty()) {
            txt_error.setVisible(true);
            lbl_password.setVisible(true);
            txt_password.setFocusColor(Color.web("#FF515A"));
            txt_password.setUnFocusColor(Color.web("#FF515A"));
            txt_password.setStyle("-fx-text-fill:#FF515A");
            isEmpty = Boolean.TRUE;
        }

        if(isEmpty){
            return true;
        }else{
            return false;
        }

    }
    private void nullErrorUI(JFXTextField field, Text error, FontAwesomeIconView label){
        error.setVisible(true);
        label.setVisible(true);
        field.setFocusColor(Color.web("#FF515A"));
        field.setUnFocusColor(Color.web("#FF515A"));
        field.setStyle("-fx-text-fill:#FF515A");
    }

    public void setInEditMode(Boolean inEditMode) {
        isInEditMode = inEditMode;
    }
}

