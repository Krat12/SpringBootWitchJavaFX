package com.diplom.electronicrecord;

import com.diplom.electronicrecord.config.StageManager;
import com.diplom.electronicrecord.view.FxmlViewLogin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;


@SpringBootApplication
public class MainApp extends Application {

    private ConfigurableApplicationContext springContext;
    private StageManager stageManager;
    private Stage splashScreen;

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
            Platform.runLater(this::showSplash);
            springContext = springBootApplicationContext();
            Platform.runLater(this::closeSplash);

    }

    @Override
    public void start(Stage stage)  {
        stageManager = springContext.getBean(StageManager.class, stage);
        displayInitialScene();
    }

    @Override
    public void stop() {
        springContext.close();
    }

    private void displayInitialScene() {

        stageManager.switchScene(springContext.getBean(FxmlViewLogin.class));
    }


    private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(MainApp.class);
        String[] args = getParameters().getRaw().toArray(new String[0]);
        return builder.run(args);
    }

    private void showSplash() {
        try {

            splashScreen = new Stage(StageStyle.TRANSPARENT);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ProgressBar.fxml"));
            Scene scene = new Scene(root, Color.TRANSPARENT);
            splashScreen.setScene(scene);
            splashScreen.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void closeSplash() {
        splashScreen.close();
    }

}
