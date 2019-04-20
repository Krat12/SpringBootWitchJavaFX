package com.diplom.electronicrecord.model;

import javafx.beans.property.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "groupstud")
public class Group {


    private LongProperty id = new SimpleLongProperty();

    @Size(min = 1,max = 100)
    @NotNull
    private StringProperty groupName = new SimpleStringProperty();

    @Max(3000)
    @Min(2000)
    @NotNull
    private IntegerProperty year = new SimpleIntegerProperty();

    private String status;

    private List<Student> students;

    @NotNull
    private Speciality speciality;

    private List<Statement> statements;

    private List<SubjectTeacherGroup> teacherGroups;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    public List<SubjectTeacherGroup> getTeacherGroups() {
        return teacherGroups;
    }

    public void setTeacherGroups(List<SubjectTeacherGroup> teacherGroups) {
        this.teacherGroups = teacherGroups;
    }

    public Group() {
    }


    @JoinColumn(name = "speciality_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Speciality getSpeciality() {
        return speciality;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.LAZY)
    public List<Student> getStudents() {
        return students;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "group")
    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id.get();
    }

    public void setSpeciality(Speciality speciality) { this.speciality = speciality; }

    public void setId(Long id) {
        this.id.set(id);
    }

    public String getGroupName() { return groupName.get();
    }

    public void setGroupName(String groupName) {
        this.groupName.set(groupName);
    }

    public Integer getYear() {
        return year.get();
    }

    public void setYear(Integer year) {
        if(year != null) {
            this.year.set(year);
        }
    }


    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return groupName.get();
    }
}
