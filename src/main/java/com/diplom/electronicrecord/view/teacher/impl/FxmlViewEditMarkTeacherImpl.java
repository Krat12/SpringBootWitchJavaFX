package com.diplom.electronicrecord.view.teacher.impl;

import com.diplom.electronicrecord.view.teacher.FxmlViewMarkTeacher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("EditMark")
public class FxmlViewEditMarkTeacherImpl implements FxmlViewMarkTeacher {

    @Override
    public String getTitle() {
        return "Оценки";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/teacher/StudentTeacher.fxml";
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
        return 350;
    }
}
