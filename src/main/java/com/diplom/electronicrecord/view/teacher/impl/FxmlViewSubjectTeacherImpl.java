package com.diplom.electronicrecord.view.teacher.impl;

import com.diplom.electronicrecord.view.teacher.FxmlViewSubjectTeacher;
import org.springframework.stereotype.Component;

@Component
public class FxmlViewSubjectTeacherImpl implements FxmlViewSubjectTeacher {

    @Override
    public String getTitle() {
        return "Список предметов";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/teacher/SubjectTeacher.fxml";
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
