package com.diplom.electronicrecord.service;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Student;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentService extends GenericService<Student> {

    List<Student> findAllStudentByGroupId(Long id);

    List<Student> findAllStudentByGroupIdAndFullName(Long id, String name);

    void checkExistStudent(String login, String password) throws AlreadyExistException;

    void saveAll(List<Student> studentList) throws AlreadyExistException;

    List<Student>readerCSV(String patch, Group group, int size) throws Exception;

    List<Student>findByGroupIsNullOrderBySurname();

    void saveNoCheck(Student student);

    List<Student> findStudentsByTeacherId(Long teacherId);

    List<Student> findStudentsByTeacherIdAndStartWitch(Long teacherId, String start);
}
