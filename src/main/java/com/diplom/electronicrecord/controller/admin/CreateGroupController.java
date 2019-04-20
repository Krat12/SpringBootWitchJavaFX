package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Speciality;
import com.diplom.electronicrecord.service.GroupService;
import com.diplom.electronicrecord.service.SpecialityService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.util.ValidationUtil;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.ValidationException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Boolean.FALSE;

@Controller
public class CreateGroupController implements Initializable {

    private final ObservableList<Speciality> list = FXCollections.observableArrayList();
    private boolean isPass = false;

    @FXML
    private JFXTextField txt_nameGroup;

    @FXML
    private ComboBox<Speciality> specialityComboBox;

    @FXML
    private JFXCheckBox CheckSpeciality;

    @FXML
    private JFXTextField txt_year;

    @FXML
    private TextField txt_speciality;

    private Boolean isInEditMode = FALSE;

    private final GroupService groupService;

    private final SpecialityService specialityService;

    private Group group;

    @Autowired
    public CreateGroupController(GroupService groupService,SpecialityService speciality) {
        this.groupService = groupService;
        this.specialityService = speciality;
    }

    @FXML
    void CheckComboBox(ActionEvent event) {

        if (CheckSpeciality.isSelected()) {
            txt_speciality.setVisible(true);
            specialityComboBox.setVisible(false);
        } else {
            txt_speciality.setVisible(false);
            specialityComboBox.setVisible(true);
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        AlertMaker.closeStage(event);
    }

    @FXML
    void save(ActionEvent event) {
        if (isInEditMode) {
            handleEditOperation();
            isClose(event);
        } else {
            handleAddOperation();
            isClose(event);
        }
    }

    private void isClose(ActionEvent event){
        if(isPass){
            AlertMaker.closeStage(event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSpecialityInComboBox();
        ValidationUtil.textFieldIsDigit(txt_year);
        loadGroup();
        fieldUpperCase();
    }

    private void fieldUpperCase(){
        txt_nameGroup.textProperty().addListener((observable, oldValue, newValue) -> {
           txt_nameGroup.setText(newValue.toUpperCase());
        });
    }


    private void loadSpecialityInComboBox() {
        list.clear();
        list.addAll(specialityService.findAll());
        specialityComboBox.setItems(list);

    }

    protected void setGroup(Group group) {
        this.group = group;
        isInEditMode = Boolean.TRUE;
    }

    private void loadGroup() {
        if (isInEditMode) {
            txt_nameGroup.setText(group.getGroupName());
            txt_year.setText(String.valueOf(group.getYear()));
            specialityComboBox.getSelectionModel().select
                    (specialityService.findSpecialityByGroup(group.getGroupName()));
        }
    }


    private void handleEditOperation() {
        try {
            if (CheckSpeciality.isSelected()) {
                Speciality speciality = new Speciality(txt_speciality.getText());
                groupService.update(getGroup(speciality));

            } else {
                Speciality speciality = specialityComboBox.getSelectionModel().getSelectedItem();
                groupService.update(getGroup(speciality));
            }

            isPass = true;
        } catch (ValidationException | AlreadyExistException validateException) {
            AlertMaker.showErrorMessage("Ошибка при изменении",validateException.getMessage());
            isPass = false;
        }

    }

    private void handleAddOperation() {
        try {
            if (CheckSpeciality.isSelected()) {
                Speciality speciality = new Speciality(txt_speciality.getText());
                groupService.save(getGroup(speciality));
            } else {
                Speciality speciality = specialityComboBox.getSelectionModel().getSelectedItem();
                groupService.save(getGroup(speciality));
            }
            isPass = true;
        } catch (ValidationException | AlreadyExistException validateException) {
            AlertMaker.showErrorMessage("Ошибка при добавлении",validateException.getMessage());
            isPass = false;
        }
    }

    private int getYear() {
        if (txt_year.getText().trim().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(txt_year.getText());
        }
    }

    private Group getGroup(Speciality speciality) {
        if(group == null){
            group = new Group();
        }
        group.setYear(getYear());
        group.setGroupName(txt_nameGroup.getText());
        group.setSpeciality(speciality);
        group.setStatus("обучаются");
        return group;
    }

    public void setInEditMode(Boolean inEditMode) {
        isInEditMode = inEditMode;
    }
}

