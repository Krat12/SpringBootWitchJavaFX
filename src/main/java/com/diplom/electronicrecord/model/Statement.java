package com.diplom.electronicrecord.model;

import javafx.beans.property.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Statement {


    private LongProperty id = new SimpleLongProperty();

    private Date date;

    private StringProperty type = new SimpleStringProperty();

    private IntegerProperty hours = new SimpleIntegerProperty();

    private Group group;

    private Subject subject;

    private Teacher teacher;

    private List<Marks> marks;

    public Statement() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    @Temporal(TemporalType.DATE)
    public Date getDate(){
        return date;
    }

    @OneToMany(mappedBy = "statement",cascade = CascadeType.ALL)
    public List<Marks> getMarks() {
        return marks;
    }

    public void setMarks(List<Marks> marks) {
        this.marks = marks;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public Integer getHours() {
        return hours.get();
    }

    public void setHours(Integer hours) {
        this.hours.set(hours);
    }

    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statement statement = (Statement) o;
        return id.get() == statement.id.get();
    }

    @Override
    public String toString() {
        return type.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.get());
    }
}
