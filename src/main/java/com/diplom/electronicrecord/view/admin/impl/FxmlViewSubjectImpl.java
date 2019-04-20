package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Subject")
public class FxmlViewSubjectImpl implements FxmlViewGroup {
    @Override
    public String getTitle() {
        return "Список предметов";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/SubjectTeacherList.fxml";
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
