package com.diplom.electronicrecord.TestFX.pages;

import com.diplom.electronicrecord.TestFX.JavaFXIds;
import com.diplom.electronicrecord.TestFX.TestFXBase;


public class GroupPage {

    private TestFXBase driver;

    public GroupPage(TestFXBase testFXBase) {
        this.driver = testFXBase;
    }

    public GroupPage clickEdit(){
        driver.clickOn(JavaFXIds.GROUP_LIST_EDIT);
        return this;
    }

    public GroupPage clickStudent(){
        driver.clickOn("ПР15-04");
        return this;
    }



}
