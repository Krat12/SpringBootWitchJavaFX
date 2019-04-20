package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewReport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Statement")
public class FxmlViewStatementImpl implements FxmlViewReport {
    @Override
    public String getTitle() {
        return "Ведомость";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/StatementList.fxml";
    }

    @Override
    public boolean getResizable() {
        return true;
    }

    @Override
    public int getMinHeight() {
        return 360;
    }

    @Override
    public int getMinWidth() {
        return 480;
    }
}
