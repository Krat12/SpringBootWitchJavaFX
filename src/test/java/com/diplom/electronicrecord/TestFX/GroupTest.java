package com.diplom.electronicrecord.TestFX;

import com.diplom.electronicrecord.TestFX.pages.GroupPage;
import com.diplom.electronicrecord.TestFX.pages.HomePage;
import com.diplom.electronicrecord.TestFX.pages.LoginPage;
import javafx.scene.control.Label;
import org.junit.Before;
import org.junit.Test;

import static com.diplom.electronicrecord.TestFX.JavaFXIds.*;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class GroupTest extends TestFXBase{

    private LoginPage loginPage;
    private HomePage homePage;
    private GroupPage groupPage;

    @Before
    public void beforeEachGroupTest() {
        homePage = new HomePage(this);
        loginPage = new LoginPage(this);
        groupPage = new GroupPage(this);
    }
    @Test
    public void editTestErrorDialog(){
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        homePage.clickGroup();
        groupPage.clickEdit();

        verifyThat(ALERT_DIALOG, (Label label) -> {
            String logText = label.getText();
            return logText.contains("Группа не выбрана!");
        });
    }

    @Test
    public void editStudentTest(){
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        homePage.clickGroup();
        groupPage.clickStudent();
        groupPage.clickEdit();
        assertTrue("Create Group window should have shown after Button click Edit",
                getPrimaryStage().isShowing());

    }




}
