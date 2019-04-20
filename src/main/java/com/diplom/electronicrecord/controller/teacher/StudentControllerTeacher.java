package com.diplom.electronicrecord.controller.teacher;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.controller.admin.StudentController;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Student;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import com.diplom.electronicrecord.view.teacher.FxmlViewReportTeacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class StudentControllerTeacher extends StudentController implements Initializable {

    private final SpringFXMLLoader springFXMLLoader;

    private final FxmlViewReportTeacher createReport;

    private final CreateReportController createReportController;

    @Autowired
    public StudentControllerTeacher(SpringFXMLLoader springFXMLLoader,
                                    @Qualifier("CreateReport") FxmlViewReportTeacher createReport,
                                    CreateReportController createReportController) {
        this.springFXMLLoader = springFXMLLoader;
        this.createReport = createReport;
        this.createReportController = createReportController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadStudentInTable();
        changeTextField();
    }

    @FXML
    void handleCreateStatement(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        StageManager stageManager = new StageManager(springFXMLLoader, stage);
        createReportController.setGroup(getGroup());
        stageManager.switchScene(createReport);
    }

    @Override
    protected void initCol() {
        numberStudent.setCellValueFactory(new PropertyValueFactory<>("numberStudent"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        numberRecord.setCellValueFactory(new PropertyValueFactory<>("numberBook"));
    }


    @Override
    protected void setGroup(Group group) {
        super.setGroup(group);
    }

    @Override
    protected void exportCSV(ActionEvent event) {
            String path = AlertMaker.initFileChooserExport(
                    "CSV (разделители - точка с запятой) (*.csv)", "*.csv");
            if (!path.isEmpty()) {
                try {
                    HandlerCSVUtil.exportFile(getValuesCSV(), getHeaderCSV(), ';', path);
                } catch (IOException e) {
                    AlertMaker.showErrorMessage("Ooopss...","Что-то полшло не так");
                }
            }
    }

    private List<String> getHeaderCSV() {
        List<String> strings = new ArrayList<>();
        strings.add("№");
        strings.add("ФИО");
        strings.add("Номер зачетки");
        return strings;
    }

    private List<String[]> getValuesCSV() {
        List<String[]> strings = new ArrayList<>();
        for (Student student : students) {
            String[] line = new String[3];
            line[0] = String.valueOf(student.getNumberStudent());
            line[1] = student.getSurname() + " " + student.getName() + " " + student.getPatronymic();
            line[2] = String.valueOf(student.getNumberBook());
            strings.add(line);
        }
        return strings;
    }
}
