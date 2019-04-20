package com.diplom.electronicrecord.config;

import com.diplom.electronicrecord.view.FxmlViewManagerWindowObject;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.File;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {

    private static final Logger LOG = getLogger(StageManager.class);
    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;
    private final String ICON_IMAGE_LOC = "src/main/resources/image/round-button-blue-glossy-download-png-93250.png";


   public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public void switchScene(final FxmlViewManagerWindowObject view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view.getTitle(),view.getResizable(),view.getMinHeight(),view.getMinWidth());
    }

    private void show(final Parent rootNode, String title,boolean resizable,int minHeight , int minWidth) {
        Scene scene = prepareScene(rootNode);
        primaryStage.setTitle(title);
        primaryStage.setResizable(resizable);
        primaryStage.setMinWidth(minWidth);
        primaryStage.setMinHeight(minHeight);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();


        try {
            primaryStage.getIcons().add(new Image(new File(ICON_IMAGE_LOC).toURI().toString()));
            primaryStage.show();

        } catch (Exception exception) {
            logAndExit("Unable to show scene for title" + title, exception);
        }
    }


    private Scene prepareScene(Parent rootNode) {
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootNode);
        }
        scene.setRoot(rootNode);
        return scene;
    }

    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }


    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }


}
