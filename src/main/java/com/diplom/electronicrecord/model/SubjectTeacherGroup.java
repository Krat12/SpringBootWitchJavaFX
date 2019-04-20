package com.diplom.electronicrecord.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subject_teacher_group")
public class SubjectTeacherGroup {


    private LongProperty id = new SimpleLongProperty();

    private Subject subject;

    private Group group;

    private Teacher teacher;

    private Integer hours;

    public SubjectTeacherGroup() {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    @JoinColumn(name = "subject_id",referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @JoinColumn(name = "group_id",referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    @JoinColumn(name = "teacher_id",referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }


    @Override
    public String toString() {
        return "SubjectTeacherGroup{" +
                "id=" + id +
                ", subject=" + subject +
                ", group=" + group +
                ", teacher=" + teacher +
                '}';
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectTeacherGroup that = (SubjectTeacherGroup) o;
        return id.get() == that.id.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.get());
    }
}
