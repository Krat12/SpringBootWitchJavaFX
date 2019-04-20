package com.diplom.electronicrecord.service;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Teacher;

import java.io.IOException;
import java.util.List;

public interface TeacherService extends GenericService<Teacher> {

    Teacher findBySurnameAndNameAndPatronymic(String surname,String name,String patr);

    List<Teacher> findTeacherByLater(String start);

    List<String> getHeaderCSV();

    List<String[]> getValuesCSV(List<Teacher> teachers);

    List<Teacher> rederCSV (String patch) throws Exception;

    void saveAll(List<Teacher> teacherList) throws AlreadyExistException;

    Teacher findTeacherById(Long id);
}
