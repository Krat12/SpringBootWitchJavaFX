package com.diplom.electronicrecord.view.admin.impl;

import com.diplom.electronicrecord.view.admin.FxmlViewSpeciality;
import org.springframework.stereotype.Component;

@Component
public class FxmlViewSpecialityImpl implements FxmlViewSpeciality {

    @Override
    public String getTitle() {
        return "Cписок специальностей";
    }

    @Override
    public String getFxmlFile() {
        return "/fxml/admin/Speciality.fxml";
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
