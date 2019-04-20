package com.diplom.electronicrecord.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Entity;

@Entity
public class Diplom extends Marks {

    private StringProperty thesis = new SimpleStringProperty();

    public Diplom() {
    }

    public String getThesis() {
        return thesis.get();
    }

    public void setThesis(String thesis) {
        this.thesis.set(thesis);
    }

    @Override
    public String toString() {
        return "Diplom {" +
                "thesis='" + thesis + '\'' +
                '}';
    }
}
