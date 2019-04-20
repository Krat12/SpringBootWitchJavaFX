package com.diplom.electronicrecord.controller.admin;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Speciality;
import com.diplom.electronicrecord.model.Subject;
import com.diplom.electronicrecord.service.SpecialityService;
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
public class SpecialityController implements Initializable {

    private ObservableList<Speciality> specialities = FXCollections.observableArrayList();

    private Long specialityId;

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<Speciality> tableView;

    @FXML
    private TableColumn<Speciality, String> specCol;

    @FXML
    private TableColumn<Speciality, Long> id;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TextField txt_search;

    @FXML
    private TextField txt_nameSpec;

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

    private final StageManager stageManager;

    private final FxmlViewMain main;

    private final SpringFXMLLoader springFXMLLoader;

    private final SpecialityService specialityService;

    private Boolean isReady;

    @Autowired
    public SpecialityController(@Lazy StageManager stageManager,
                                @Qualifier("Main") FxmlViewMain main,
                                SpringFXMLLoader springFXMLLoader,
                                SpecialityService specialityService) {
        this.stageManager = stageManager;
        this.main = main;
        this.springFXMLLoader = springFXMLLoader;
        this.specialityService = specialityService;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleExistKey();
        initCol();
        AlertMaker.initDrawer(drawer,hamburger,springFXMLLoader);
        loadDataInTable();
        changeText();
        btn_edit.setDisable(true);
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
    void SelectSpec(MouseEvent event) {
        Speciality speciality = tableView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && speciality != null) {
            btn_edit.setDisable(false);
            AlertMaker.setColorCommit(lbl_edit, btn_edit);
            txt_nameSpec.setText(speciality.getNameSpeciality());
            specialityId = speciality.getId();
        }
    }

    @FXML
    void closeStage(ActionEvent event) {
        stageManager.switchScene(main);
    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void exportCSV(ActionEvent event) throws IOException {
        String path = AlertMaker.initFileChooserExport(
                "CSV (разделители - точка с запятой) (*.csv)", "*.csv");

        if (!path.isEmpty()) {
            HandlerCSVUtil.exportFile(specialityService.getValuesCSV(specialities),
                    specialityService.getHeaderCSV(), ';', path);
        }
    }

    @FXML
    void handleCommit(ActionEvent event) {
        if (isReady) {
            List<Speciality> saveSpec = new ArrayList<>();
            try {
                for (Speciality speciality : specialities) {
                    if (speciality.getId() == null) {
                      Speciality specialityCheck = specialityService.findByNameSpeciality(speciality.getNameSpeciality());
                       if(specialityCheck != null){ throw new AlreadyExistException
                               ("Специальность "+speciality.getNameSpeciality()+" уже существует");}
                        saveSpec.add(speciality);
                    }
                }
                specialityService.saveAll(saveSpec);
                handleRefresh(new ActionEvent());
            } catch (AlreadyExistException e) {
                AlertMaker.showMaterialDialog(rootPane,contentPane,"Ошибка при добавлении",e.getMessage());
            }
        }
    }

    @FXML
    void handleOut(ActionEvent event) {stageManager.switchScene(main);}

    @FXML
    void handleRefresh(ActionEvent event) {
        btn_edit.setDisable(true);
        txt_nameSpec.setText("");
        AlertMaker.resetColorCommit(lbl_commit,lbl_rollback,btn_commit,btn_rollback);
        AlertMaker.resetColorCommit(lbl_edit,btn_edit);
        loadDataInTable();
    }

    @FXML
    void handleRollback(ActionEvent event) {
        if (isReady){
            handleRefresh(new ActionEvent());
        }
    }

    @FXML
    void handleSpecAdd(ActionEvent event) {
        Speciality speciality = new Speciality();
        speciality.setNameSpeciality(txt_nameSpec.getText());
        try {
            specialityService.save(speciality);
            handleRefresh(new ActionEvent());
        } catch (ValidationException |AlreadyExistException e) {
            AlertMaker.showMaterialDialog(rootPane,contentPane,"Ошибка при добавлении",e.getMessage());
        }
    }

    @FXML
    void handleSpecDeleteOption(ActionEvent event) {
        Speciality speciality = tableView.getSelectionModel().getSelectedItem();
        if (speciality == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, "Специальность не выбрана!",
                    "Пожалуйста, выберите Специальность");
            return;
        }

        Alert alert = new Alert(CONFIRMATION);
        alert.setTitle("Удаление специальности ");
        alert.setHeaderText("Удалить специальность " + speciality.getNameSpeciality() + " ?");
        alert.setContentText("Вы действительно хотите удалить специальность?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            specialityService.delete(speciality);
            handleRefresh(new ActionEvent());
        }
    }

    @FXML
    void handleSpecEditOption(ActionEvent event) {
        Speciality speciality = new Speciality();
        speciality.setId(specialityId);
        speciality.setNameSpeciality(txt_nameSpec.getText());
        try {
            specialityService.update(speciality);
            handleRefresh(new ActionEvent());
        } catch (ValidationException |AlreadyExistException e) {
                AlertMaker.showMaterialDialog(rootPane,contentPane,"Ошибка при изменении",e.getMessage());
        }
    }

    private void handleExistKey() {
        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stageManager.switchScene(main);
            }
        });

    }

    private void loadDataInTable(){
        specialities.clear();
        specialities.addAll(specialityService.findAll());
        tableView.setItems(specialities);
    }

    private void initCol() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        specCol.setCellValueFactory(new PropertyValueFactory<>("nameSpeciality"));
    }

    private void changeText() {
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            specialities.clear();
            specialities.addAll(specialityService.findByNameSpecialityStartingWith(newValue));
            tableView.setItems(specialities);
        });
    }

    private void readerCSV(String patch) {
        try {
            specialities.addAll(specialityService.readerCSV(patch));
            isReady = Boolean.TRUE;
            AlertMaker.setColorCommitAndRollBack(lbl_commit,lbl_rollback,btn_commit,btn_rollback);
        } catch (Exception e) {
            AlertMaker.showErrorMessage("Ошибка при чтении файла", "Возможно несовподение типов или пустой");
        }
    }

}