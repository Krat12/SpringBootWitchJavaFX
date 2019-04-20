package com.diplom.electronicrecord.view.admin.impl;


import com.diplom.electronicrecord.view.admin.FxmlViewMain;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Main")
public class FxmlViewHomeImpl implements FxmlViewMain {


    @Override
    public String getTitle() {
        return "Главное окно";
    }

    @Override
    public String getFxmlFile() {

        return "/fxml/admin/Home.fxml";
    }

    @Override
    public boolean getResizable() {
        return true;
    }

    @Override
    public int getMinHeight() {
        return 400;
    }

    @Override
    public int getMinWidth() {
        return 470;
    }
}
