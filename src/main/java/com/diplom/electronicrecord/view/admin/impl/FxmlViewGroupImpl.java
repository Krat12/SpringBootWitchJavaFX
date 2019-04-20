package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Group")
public class FxmlViewGroupImpl implements FxmlViewGroup {


    @Override
    public String getTitle() {
        return "Список групп";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/GroupForAdmin.fxml";
    }

    @Override
    public boolean getResizable() {
        return true;
    }

    @Override
    public int getMinHeight() {
        return 200;
    }

    @Override
    public int getMinWidth() {
        return 200;
    }
}
