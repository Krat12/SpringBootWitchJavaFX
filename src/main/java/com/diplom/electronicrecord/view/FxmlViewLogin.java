package com.diplom.electronicrecord.view;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Login")
public class FxmlViewLogin implements FxmlViewManagerWindowObject {


    @Override
    public String getTitle() {
        return "Авторизация";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/Login.fxml";
    }


    @Override
    public boolean getResizable() {
        return true;
    }

    @Override
    public int getMinHeight() {
        return 380;
    }

    @Override
    public int getMinWidth() {
        return 470;
    }
}
