package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewCreate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("CreateSubject")
public class FxmlViewCreateSubjectImpl implements FxmlViewCreate {
    @Override
    public String getTitle() {
        return "Добавление или ридоктирование придмета";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/CreateSubjectGroup.fxml";
    }

    @Override
    public boolean getResizable() {
        return false;
    }

    @Override
    public int getMinHeight() {
        return 0;
    }

    @Override
    public int getMinWidth() {
        return 0;
    }
}
