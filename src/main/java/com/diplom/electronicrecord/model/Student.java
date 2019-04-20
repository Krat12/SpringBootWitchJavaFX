package com.diplom.electronicrecord.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Student extends User{

    private IntegerProperty numberBook = new SimpleIntegerProperty();

    @NotNull
    private Group group;


    private List<Marks> marks;

    private IntegerProperty numberStudent = new SimpleIntegerProperty();


    @Transient
    public Integer getNumberStudent() {
        return numberStudent.get();
    }

    public void setNumberStudent(Integer numberStudent) {
        this.numberStudent.set(numberStudent);
    }

    @JoinColumn(name = "group_id",referencedColumnName = "id")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "student")
    public List<Marks> getMarks() {
        return marks;
    }

    public void setMarks(List<Marks> marks) {
        this.marks = marks;
    }

    public Student() {
    }


    public Integer getNumberBook() {
        return numberBook.get();
    }

    public void setNumberBook(Integer numberBook) {
        this.numberBook.set(numberBook);
    }


    @Override
    public String toString() {
        return getSurname() + " " + getName() + " " + getPatronymic();
    }

}
