package com.diplom.electronicrecord.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
public class Subject {


    private LongProperty id = new SimpleLongProperty();

    @Size(min = 1,max = 255)
    private StringProperty nameSubject = new SimpleStringProperty();

    private List<Statement> statements;

    private List<SubjectTeacherGroup> teacherGroups;

    public Subject() {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL)
    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public String getNameSubject() {
        return nameSubject.get();
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject.set(nameSubject);
    }
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "subject")
    public List<SubjectTeacherGroup> getTeacherGroups() {
        return teacherGroups;
    }

    public void setTeacherGroups(List<SubjectTeacherGroup> teacherGroups) {
        this.teacherGroups = teacherGroups;
    }

    @Override
    public String toString() {
        return nameSubject.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id.get() == subject.id.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.get());
    }
}
