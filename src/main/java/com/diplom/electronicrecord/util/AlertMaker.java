package com.diplom.electronicrecord.util;

import com.diplom.electronicrecord.config.SpringFXMLLoader;
import com.diplom.electronicrecord.controller.admin.GroupController;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlertMaker {

    private static final String ICON_IMAGE_LOC = "/image/round-button-blue-glossy-download-png-93250.png";


    public static void showSimpleAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        styleAlert(alert);
        alert.showAndWait();
    }

    public static void showErrorMessage(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        styleAlert(alert);
        alert.showAndWait();
    }

    public static void showErrorMessage(Exception ex, String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error occurred");
        alert.setHeaderText(title);
        alert.setContentText(content);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    public static void showMaterialDialog(StackPane root, Node nodeToBeBlurred, String header, String body) {
        BoxBlur blur = new BoxBlur(3, 3, 3);


        JFXButton jfxButton = new JFXButton("OK");
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);


        jfxButton.getStyleClass().add("dialog-button");
        dialog.setId("ButtonDialog");
        jfxButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            dialog.close();
        });

        Label label = new Label(header);
        label.setId("header");
        dialogLayout.setHeading(label);
        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(jfxButton);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
            nodeToBeBlurred.setEffect(null);
        });
        nodeToBeBlurred.setEffect(blur);
    }

    public static void showTrayMessage(String title, String message) {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            BufferedImage image = ImageIO.read(AlertMaker.class.getResource(ICON_IMAGE_LOC));
            TrayIcon trayIcon = new TrayIcon(image, "Library Assistant");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Library Assistant");
            tray.add(trayIcon);
            trayIcon.displayMessage(title, message, MessageType.INFO);
            tray.remove(trayIcon);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private static void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        setStageIcon(stage);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertMaker.class.getResource("/styles/theme.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }

    public static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(ICON_IMAGE_LOC));
    }

    public static void closeStage(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public static void setColorCommitAndRollBack(FontAwesomeIconView commit, FontAwesomeIconView rollback, JFXButton btn_commit, JFXButton btn_rollback) {
        commit.setStyle("-fx-fill:#70ff7e");
        rollback.setStyle("-fx-fill:#ff6161");


        btn_commit.setStyle("-fx-text-fill:#70ff7e");
        btn_rollback.setStyle("-fx-text-fill:#ff6161");

        commit.setOpacity(1);
        rollback.setOpacity(1);
    }

    public static void setColorCommit(FontAwesomeIconView commit, JFXButton btn_commit) {
        commit.setStyle("-fx-fill:#70ff7e");
        btn_commit.setStyle("-fx-text-fill:#70ff7e");
    }

    public static void resetColorCommit(FontAwesomeIconView commit, JFXButton btn_commit) {
        btn_commit.setStyle("-fx-text-fill:white");
        commit.setStyle("-fx-fill:#FFFF");
    }

    public static void resetColorCommit(FontAwesomeIconView commit, FontAwesomeIconView rollback, JFXButton btn_commit, JFXButton btn_rollback) {
        commit.setStyle("-fx-fill:#FFFF");
        rollback.setStyle("-fx-fill:#FFFF");
        btn_commit.setStyle("-fx-text-fill:white");
        btn_rollback.setStyle("-fx-text-fill:white");
    }

    public static String initFileChooserImport(String description, String extension) {
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter(description, extension);
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return "";
        }

    }

    public static String initFileChooserExport(String description, String extension){
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter(description, extension);
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return "";
        }
    }

    public static void initDrawer(JFXDrawer drawer, JFXHamburger hamburger, SpringFXMLLoader springFXMLLoader){
        try {
            VBox toolbar = (VBox) springFXMLLoader.load("/fxml/admin/toolbar.fxml");
            drawer.setSidePane(toolbar);

        } catch (IOException ex) {
            Logger.getLogger(GroupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1.5);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.toFront();
        });
        drawer.setOnDrawerClosed((event) -> {
            drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });
    }

    public static Object loadWindowModality(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            stage.show();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(AlertMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

}
