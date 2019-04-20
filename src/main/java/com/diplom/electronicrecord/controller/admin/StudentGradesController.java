package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.model.*;
import com.diplom.electronicrecord.service.MarkService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
@Primary
public class StudentGradesController implements Initializable {

    protected final ObservableList<Marks> marksObservableList = FXCollections.observableArrayList();

    private static final int EMPTY = -1;

    @FXML
    protected TableView<Marks> tableView;

    @FXML
    private TableColumn<Marks, Integer> numberStudent;

    @FXML
    private TableColumn<Marks, Student> fullName;

    @FXML
    protected TableColumn<Marks, String> colMark;

    @FXML
    protected TableColumn<Marks, String> thesis;

    @FXML
    protected TableColumn<Marks, String> placePractice;

    @FXML
    protected TableColumn<Marks, String> colFullNameBoss;

    private Statement statement;

    protected MarkService markService;


    public StudentGradesController() {
    }

    @FXML
    void closeStage(ActionEvent event) {
    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void exportCSV(ActionEvent event) {
        try {
            String path = AlertMaker.initFileChooserExport(
                    "CSV (разделители - точка с запятой) (*.csv)", "*.csv");
            if (!path.isEmpty()) {

                HandlerCSVUtil.exportFile(markService.getValuesCSV(marksObservableList),
                        markService.getHeaderCSV(statement), ';', path);
            }
        } catch (IOException e) {
            AlertMaker.showErrorMessage("Что-то пошло не так...", "");
        }
    }


    protected void setStatement(Statement statement) {
        this.statement = statement;
    }

    protected Statement getStatement() {
        return statement;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadDataTable();

    }

    protected void initCol() {

        numberStudent.setCellValueFactory(new PropertyValueFactory<>("number"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("student"));
        colFullNameBoss.setCellValueFactory(new PropertyValueFactory<>("fullNameBoss"));
        placePractice.setCellValueFactory(new PropertyValueFactory<>("placePractice"));
        thesis.setCellValueFactory(new PropertyValueFactory<>("thesis"));
        colMark.setCellValueFactory(new PropertyValueFactory<>("markString"));
    }

    protected void loadDataTable() {

        marksObservableList.clear();

        int number = 1;
        System.out.println(getStatement());
        for (Marks mark : markService.findMarksByStatementId(getStatement().getId())) {

            mark.setNumber(number);

            if (mark instanceof CourseWork) {
                placePractice.setVisible(true);
                colFullNameBoss.setVisible(true);
            }

            if (mark instanceof Diplom) {
                thesis.setVisible(true);
            }

            if (statement.getType().equals("Зачет")) {
                convertToString(mark);
            } else {

                if (mark.getMark() != EMPTY) {

                    if (mark.getMark() == 0) {
                        mark.setMarkString("н/а");
                    } else {
                        mark.setMarkString(String.valueOf(mark.getMark()));
                    }

                }

            }

            number++;
            marksObservableList.add(mark);
        }
        tableView.setItems(marksObservableList);
    }

    private void convertToString(Marks mark) {
        if (mark.getMark() == EMPTY) {
            return;
        }

        if (mark.getMark() == 0) {

            mark.setMarkString("Незачет");
        } else {
            mark.setMarkString("Зачет");
        }
    }

    @Autowired
    public void setMarkService(MarkService markService) {
        this.markService = markService;
    }
}
