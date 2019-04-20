package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewCreate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("CreateGroup")
public class FxmlViewCreateGroupImpl implements FxmlViewCreate {
    @Override
    public String getTitle() {
        return "Добавление или редоктирование группы";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/CreateGroup.fxml";
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
