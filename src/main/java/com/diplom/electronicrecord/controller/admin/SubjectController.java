package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Subject;
import com.diplom.electronicrecord.service.SubjectService;
import com.diplom.electronicrecord.util.AlertMaker;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import com.diplom.electronicrecord.view.admin.FxmlViewMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javax.validation.ValidationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

@Controller
public class SubjectController implements Initializable {


    private ObservableList<Subject> subjects = FXCollections.observableArrayList();

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<Subject> tableView;

    @FXML
    private TableColumn<Subject, String> SubjectCol;

    @FXML
    private TableColumn<Subject, Long> id;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TextField txt_nameSubject;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private FontAwesomeIconView lbl_edit;

    @FXML
    private JFXButton btn_commit;

    @FXML
    private FontAwesomeIconView lbl_commit;

    @FXML
    private JFXButton btn_rollback;

    @FXML
    private FontAwesomeIconView lbl_rollback;

    private Long subjectId;

    private Boolean isReady = Boolean.FALSE;

    private final SpringFXMLLoader springFXMLLoader;

    private final SubjectService subjectService;

    private final StageManager stageManager;

    private final FxmlViewMain main;

    @Autowired
    public SubjectController(SpringFXMLLoader springFXMLLoader,
                             SubjectService subjectService,
                             @Lazy StageManager stageManager,
                             @Qualifier("Main") FxmlViewMain main) {
        this.springFXMLLoader = springFXMLLoader;
        this.subjectService = subjectService;
        this.stageManager = stageManager;
        this.main = main;
    }

    @FXML
    void SelectSubject(MouseEvent event) {
        Subject subject = tableView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && subject != null) {
            btn_edit.setDisable(false);
            AlertMaker.setColorCommit(lbl_edit, btn_edit);
            txt_nameSubject.setText(subject.getNameSubject());
            subjectId = subject.getId();
        }
    }

    @FXML
    void closeStage(ActionEvent event) {

    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void exportCSV(ActionEvent event) throws IOException {

        String path = AlertMaker.initFileChooserExport(
                "CSV (разделители - точка с запятой) (*.csv)", "*.csv");

        if (!path.isEmpty()) {
            HandlerCSVUtil.exportFile(subjectService.getValuesCSV(subjects),
                    subjectService.getHeaderCSV(), ';', path);
        }

    }

    @FXML
    void ImportCSV(ActionEvent event) {
        String path = AlertMaker.initFileChooserImport
                ("CSV (разделители - точка с запятой) (*.csv)", "*.csv");
        if (!path.isEmpty()) {
            readerCSV(path);
        }
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        AlertMaker.resetColorCommit(lbl_edit, btn_edit);
        btn_edit.setDisable(true);
        txt_nameSubject.setText("");
        AlertMaker.resetColorCommit(lbl_commit, lbl_rollback, btn_commit, btn_rollback);
        isReady = false;
        loadDataInTable();
    }

    @FXML
    void handleSubjectAdd(ActionEvent event) {
        Subject subject = new Subject();
        subject.setNameSubject(txt_nameSubject.getText());
        try {
            subjectService.save(subject);
            handleRefresh(new ActionEvent());
        } catch (ValidationException | AlreadyExistException e) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, "Ошибка при изменении", e.getMessage());
        }
    }

    @FXML
    void handleSubjectDeleteOption(ActionEvent event) {
        Subject subject = tableView.getSelectionModel().getSelectedItem();
        if (subject == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, "Предмет не выбран!",
                    "Пожалуйста, выберите предмет");
            return;
        }

        Alert alert = new Alert(CONFIRMATION);
        alert.setTitle("Удаление предмета ");
        alert.setHeaderText("Удалить придмет " + subject.getNameSubject() + " ?");
        alert.setContentText("Вы действительно хотите удалить придмет?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            subjectService.delete(subject);
            handleRefresh(new ActionEvent());
        }
    }

    @FXML
    void handleSubjectEditOption(ActionEvent event)  {
        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setNameSubject(txt_nameSubject.getText());
        try {
            subjectService.update(subject);
            handleRefresh(new ActionEvent());
        } catch (ValidationException | AlreadyExistException e) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, "Ошибка при изменении", e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AlertMaker.initDrawer(drawer, hamburger, springFXMLLoader);
        initCol();
        loadDataInTable();
        handleExistKey();
        btn_edit.setDisable(true);
        changeText();
    }

    private void initCol() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        SubjectCol.setCellValueFactory(new PropertyValueFactory<>("nameSubject"));
    }

    private void loadDataInTable() {
        subjects.clear();
        subjects.addAll(subjectService.findAll());
        tableView.setItems(subjects);
    }

    private void handleExistKey() {
        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stageManager.switchScene(main);
            }
        });

    }

    private void changeText() {
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            subjects.clear();
            subjects.addAll(subjectService.findByNameSubjectStartingWith(newValue));
            tableView.setItems(subjects);
        });
    }

    private void readerCSV(String patch) {
        try {
            subjects.addAll(subjectService.readerCSV(patch));
            isReady = Boolean.TRUE;
            AlertMaker.setColorCommitAndRollBack(lbl_commit,lbl_rollback,btn_commit,btn_rollback);
        } catch (IOException e) {
            AlertMaker.showErrorMessage("Ошибка при чтении файла", "Возможно файл поврежден или не тот формат");
        } catch (Exception e) {
            AlertMaker.showErrorMessage("Ошибка при чтении файла", "Возможно несовподение типов или пустой");

        }
    }

    @FXML
    void handleOut(ActionEvent event) {
        stageManager.switchScene(main);
    }

    @FXML
    void handleRollback(ActionEvent event) {
        if (isReady) {
            handleRefresh(new ActionEvent());
        }
    }


    @FXML
    void handleCommit(ActionEvent event) {
        if (isReady) {
            List<Subject> saveSubjects = new ArrayList<>();
            try {
                for (Subject subject : subjects) {
                    if (subject.getId() == 0) {
                        subjectService.checkExistSubject(subject.getNameSubject());
                        saveSubjects.add(subject);
                    }
                }
                subjectService.saveAllSubject(saveSubjects);
                handleRefresh(new ActionEvent());
            } catch (AlreadyExistException e) {
                AlertMaker.showMaterialDialog(rootPane,contentPane,"Ошибка при добавлении",e.getMessage());
            }
        }
    }

}
