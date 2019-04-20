package com.diplom.electronicrecord.view.teacher.impl;

import com.diplom.electronicrecord.view.teacher.FxmlViewReportTeacher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("CreateReport")
public class FxmlViewReportTeacherImpl implements FxmlViewReportTeacher {

    @Override
    public String getTitle() {
        return "Создание ведомости";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/teacher/AddStatement.fxml";
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
