package com.diplom.electronicrecord.TestFX;

import com.diplom.electronicrecord.MainApp;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;

public class TestFXBase extends ApplicationTest {

    private Stage primaryStage;


    @Before
    public void setUpClass() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        stage.show();
    }

    @After
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }


    public <T extends Node> T find (final String query){
        return (T) lookup(query).queryAll().iterator().next();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void ensureEventQueueComplete(){
        WaitForAsyncUtils.waitForFxEvents(1);
    }
}
