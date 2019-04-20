package com.diplom.electronicrecord.view.teacher.impl;

import com.diplom.electronicrecord.view.teacher.FxmlViewMarkTeacher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Mark")
public class FxmlViewMarkTeacherImpl implements FxmlViewMarkTeacher {
    @Override
    public String getTitle() {
        return "Список студентов";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/teacher/MarkList.fxml";
    }

    @Override
    public boolean getResizable() {
        return true;
    }

    @Override
    public int getMinHeight() {
        return 350;
    }

    @Override
    public int getMinWidth() {
        return 250;
    }
}
