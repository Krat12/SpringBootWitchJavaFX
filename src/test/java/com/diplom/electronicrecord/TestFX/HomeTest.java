package com.diplom.electronicrecord.TestFX;


import com.diplom.electronicrecord.TestFX.pages.HomePage;
import com.diplom.electronicrecord.TestFX.pages.LoginPage;
import org.junit.Before;
import org.junit.Test;

import static com.diplom.electronicrecord.TestFX.JavaFXIds.VALID_PASSWORD;
import static com.diplom.electronicrecord.TestFX.JavaFXIds.VALID_USERNAME;
import static org.junit.Assert.assertTrue;


public class HomeTest extends TestFXBase {
    private LoginPage loginPage;
    private HomePage homePage;

    @Before
    public void beforeEachLoginTest() {
        homePage = new HomePage(this);
        loginPage = new LoginPage(this);
    }

    @Test
    public void groupListTest(){
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);
        homePage.clickGroup();
        assertTrue("GroupList window should have shown after Button click GroupList",
                getPrimaryStage().isShowing());
    }

    @Test
    public void reportListTest(){
        loginPage.login(VALID_USERNAME,VALID_PASSWORD);
        homePage.clickReport();
        assertTrue("Report window should have shown after Button click Report",
                getPrimaryStage().isShowing());
    }


}
