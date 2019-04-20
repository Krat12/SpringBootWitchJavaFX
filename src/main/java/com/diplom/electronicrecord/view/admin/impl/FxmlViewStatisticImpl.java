package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewStatistic;
import org.springframework.stereotype.Component;

@Component
public class FxmlViewStatisticImpl implements FxmlViewStatistic {
    @Override
    public String getTitle() {
        return "Статистика";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/Statistic.fxml";
    }

    @Override
    public boolean getResizable() {
        return true;
    }

    @Override
    public int getMinHeight() {
        return 300;
    }

    @Override
    public int getMinWidth() {
        return 350;
    }
}
