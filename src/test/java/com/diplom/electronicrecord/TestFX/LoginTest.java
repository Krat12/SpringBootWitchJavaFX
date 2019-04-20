package com.diplom.electronicrecord.TestFX;

import com.diplom.electronicrecord.TestFX.pages.LoginPage;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.transaction.Transactional;

import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class LoginTest extends TestFXBase{
    private final String VALID_USERNAME = "admin";
    private final String VALID_PASSWORD = "1234";
    private LoginPage loginPage;

    @Before
    public void beforeEachLoginTest() {
        loginPage = new LoginPage(this);

    }

    @Test
    @Transactional
    public void loginWithSuccess() {
        loginPage.login(VALID_USERNAME,VALID_PASSWORD);
        ensureEventQueueComplete();
        assertTrue("Main window should have shown after successful login",
                getPrimaryStage().isShowing());
    }
    @Test
    @Parameters(method = "parametersForIncorrectLogins")
    public void loginUnsuccessful(String username, String password) {
        String expectedStatus = "Пользователя не существует";

        loginPage.login(username, password);

        ensureEventQueueComplete();
        Assert.assertEquals("Unable to match unsuccessful login status",
                expectedStatus, loginPage.getLoginStatus().getText());
    }

    private Object[] parametersForIncorrectLogins() {
        String BAD_USERNAME = "badUser";
        String BAD_PASSWORD = "badPassword";

        return new Object[]{
                new Object[]{BAD_USERNAME, BAD_PASSWORD},
                new Object[]{BAD_USERNAME, VALID_PASSWORD},
                new Object[]{VALID_USERNAME, BAD_PASSWORD},
                new Object[]{"", ""} //EMPTY THUS NOTHING ENTERED
        };
    }
}
