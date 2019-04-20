package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("NoGroup")
public class FxmlViewStudentIsNullGroupImpl implements FxmlViewGroup {

    @Override
    public String getTitle() {
        return "Список студентов без групп";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/StudentIsNullGroup.fxml";
    }

    @Override
    public boolean getResizable() {
        return true;
    }

    @Override
    public int getMinHeight() {
        return 300;
    }

    @Override
    public int getMinWidth() {
        return 300;
    }
}
