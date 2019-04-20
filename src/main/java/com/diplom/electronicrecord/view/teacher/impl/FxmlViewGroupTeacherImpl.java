package com.diplom.electronicrecord.view.teacher.impl;

import com.diplom.electronicrecord.view.teacher.FxmlViewGroupTeacher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Group")
public class FxmlViewGroupTeacherImpl implements FxmlViewGroupTeacher {
    @Override
    public String getTitle() {
        return "Cписок групп";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/teacher/GroupTeacher.fxml";
    }

    @Override
    public boolean getResizable() {
        return true;
    }

    @Override
    public int getMinHeight() {
        return 250;
    }

    @Override
    public int getMinWidth() {
        return 250;
    }
}
