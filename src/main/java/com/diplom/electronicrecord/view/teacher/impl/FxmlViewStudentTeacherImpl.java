package com.diplom.electronicrecord.view.teacher.impl;

import com.diplom.electronicrecord.view.teacher.FxmlViewGroupTeacher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Student")
public class FxmlViewStudentTeacherImpl implements FxmlViewGroupTeacher {

    @Override
    public String getTitle() {
        return "Список студентов";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/teacher/StudentListTeacher.fxml";
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
