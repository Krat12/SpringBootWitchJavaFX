package com.diplom.electronicrecord.service;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Subject;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectService extends GenericService<Subject> {

    Subject findByNameSubject(String name);

    Subject findByNameSubjectStartingWith(String start);

    List<String[]> getValuesCSV(List<Subject> subjects);

    List<String>getHeaderCSV();

    List<Subject> readerCSV(String patch) throws Exception;

    void saveAllSubject(List<Subject> subjects);

    void checkExistSubject(String name) throws AlreadyExistException;

    List<Subject> findSubjectsByGroupAndTeacher(Long groupId, Long teacherId);
}
