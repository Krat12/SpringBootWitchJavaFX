package com.diplom.electronicrecord.service;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.SubjectTeacherGroup;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectTeacherGroupService {

    List<SubjectTeacherGroup> findAllTeacherAndSubjectByGroupId(Long groupId);

    void copyByInsert(Long target, Long source);

    SubjectTeacherGroup update(SubjectTeacherGroup teacherGroup) throws AlreadyExistException;

    void delete(SubjectTeacherGroup subjectTeacherGroup);

    List<SubjectTeacherGroup> findBySubjectNameAndFullName(Long Id, String start);

    List<SubjectTeacherGroup> readerCSV(String patch, Group group) throws Exception;

    List<SubjectTeacherGroup> findAllGroupAndSubjectByTeacherId(Long id);

}
