package com.diplom.electronicrecord.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.List;

@Entity
public class Teacher  extends User{

    private IntegerProperty position = new SimpleIntegerProperty();

    private StringProperty category = new SimpleStringProperty();

    private List<SubjectTeacherGroup> teacherGroups;

    private List<Statement> statements;

    private Teacher teacher = this;

    @OneToMany(mappedBy = "teacher",fetch =  FetchType.LAZY)
    public List<SubjectTeacherGroup> getTeacherGroups() {
        return teacherGroups;
    }

    public void setTeacherGroups(List<SubjectTeacherGroup> teacherGroups) {
        this.teacherGroups = teacherGroups;
    }

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public Integer getPosition() {
        return position.get();
    }

    public void setPosition(Integer position) {
        this.position.set(position);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    @Transient
    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {
        return getSurname() + " " + getName() + " " +getPatronymic();
    }

}
