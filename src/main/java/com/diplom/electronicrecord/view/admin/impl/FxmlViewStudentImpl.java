package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Student")
public class FxmlViewStudentImpl implements FxmlViewGroup {

    @Override
    public String getTitle() {
        return "Список студентов";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/StudentList.fxml";
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
