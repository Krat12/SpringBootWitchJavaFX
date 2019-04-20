package com.diplom.electronicrecord.TestFX.pages;

import com.diplom.electronicrecord.TestFX.JavaFXIds;
import com.diplom.electronicrecord.TestFX.TestFXBase;

public class HomePage  {

    private final TestFXBase driver;

    public HomePage(TestFXBase driver) {
        this.driver = driver;
    }

    public HomePage clickGroup(){
        driver.clickOn(JavaFXIds.HOME_BUTTON_LIST_GROUP);
        return this;
    }

    public HomePage clickReport(){
        driver.clickOn(JavaFXIds.HOME_BUTTON_LIST_REPORT);
        return this;
    }
}
