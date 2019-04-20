package com.diplom.electronicrecord.view.teacher.impl;

import com.diplom.electronicrecord.view.teacher.FxmlViewMarkTeacher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("EditDiplom")
public class FxmlVIewEditDiplomImpl implements FxmlViewMarkTeacher {
    @Override
    public String getTitle() {
        return "Изменение дипломной работы";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/teacher/EditDiplom.fxml";
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
