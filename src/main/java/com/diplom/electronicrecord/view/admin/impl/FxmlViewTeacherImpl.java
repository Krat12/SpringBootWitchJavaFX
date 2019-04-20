package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewTeacher;
import org.springframework.stereotype.Component;

@Component
public class FxmlViewTeacherImpl implements FxmlViewTeacher {
    @Override
    public String getTitle() {
        return "Список преподавателей";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/TeacherList.fxml";
    }

    @Override
    public boolean getResizable() {
        return true;
    }

    @Override
    public int getMinHeight() {
        return 340;
    }

    @Override
    public int getMinWidth() {
        return 200;
    }
}
