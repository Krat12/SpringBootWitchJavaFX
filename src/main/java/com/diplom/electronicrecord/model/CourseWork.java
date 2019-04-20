package com.diplom.electronicrecord.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Entity;

@Entity
public class CourseWork extends Marks {

    private StringProperty placePractice = new SimpleStringProperty();

    private StringProperty fullNameBoss = new SimpleStringProperty() ;

    public String getPlacePractice() {
        return placePractice.get();
    }

    public void setPlacePractice(String placePractice) {
        this.placePractice.set(placePractice);
    }

    public String getFullNameBoss() {
        return fullNameBoss.get();
    }

    public void setFullNameBoss(String fullNameBoss) {
        this.fullNameBoss.set(fullNameBoss);
    }

    @Override
    public String toString() {
        return "CourseWork{" +
                "placePractice=" + placePractice +
                ", fullNameBoss=" + fullNameBoss +
                '}';
    }

    public CourseWork() {
    }
}
