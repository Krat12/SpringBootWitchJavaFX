package com.diplom.electronicrecord.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Marks {


    private LongProperty id = new SimpleLongProperty();

    private Statement statement;

    private Student student;

    private IntegerProperty mark = new SimpleIntegerProperty();

    private IntegerProperty number =new SimpleIntegerProperty();

    private String markString;

    private Subject subject;

    public Marks() {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    @JoinColumn(name = "statement_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getMark() {
        return mark.get();
    }

    public void setMark(Integer mark) {
        if(mark != null) {
            this.mark.set(mark);
        }else {
            this.mark.set(-1);
        }
    }

    @Transient
    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }
    @Transient
    public String getMarkString() {
        return markString;
    }

    public void setMarkString(String markString) {
        this.markString = markString;
    }

    @Override
    public String toString() {
        return student.getSurname() + " " + student.getName() + " " + student.getPatronymic();
    }

    @Transient
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marks marks = (Marks) o;
        return id.get() == marks.id.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.get());
    }
}
