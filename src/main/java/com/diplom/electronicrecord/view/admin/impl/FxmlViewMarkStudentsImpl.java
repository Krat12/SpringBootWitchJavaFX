package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewReport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Mark")
public class FxmlViewMarkStudentsImpl implements FxmlViewReport {

    @Override
    public String getTitle() {
        return "Список студентов";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/MarkAdmin.fxml";
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
