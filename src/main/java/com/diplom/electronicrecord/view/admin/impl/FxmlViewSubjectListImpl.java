package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewSubject;
import org.springframework.stereotype.Controller;

@Controller
public class FxmlViewSubjectListImpl implements FxmlViewSubject {

    @Override
    public String getTitle() {
        return "Список предметов";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/SubjectList.fxml";
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
