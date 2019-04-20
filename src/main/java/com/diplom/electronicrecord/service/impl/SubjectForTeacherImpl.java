package com.diplom.electronicrecord.service.impl;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Subject;
import com.diplom.electronicrecord.model.SubjectTeacherGroup;
import com.diplom.electronicrecord.model.Teacher;
import com.diplom.electronicrecord.repository.SubjectTeacherGroupRepository;
import com.diplom.electronicrecord.service.SubjectTeacherGroupService;
import com.diplom.electronicrecord.service.SubjectService;
import com.diplom.electronicrecord.service.TeacherService;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SubjectForTeacherImpl implements SubjectTeacherGroupService {

    private final SubjectTeacherGroupRepository subjectForTeacherRepository;

    private final SubjectService subjectService;

    private final TeacherService teacherService;

    @Autowired
    public SubjectForTeacherImpl(SubjectTeacherGroupRepository subject, SubjectService subjectService,
                                 TeacherService teacherService) {
        this.subjectForTeacherRepository = subject;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @Override
    public List<SubjectTeacherGroup> findAllTeacherAndSubjectByGroupId(Long groupId) {
        return subjectForTeacherRepository.findAllTeacherAndSubjectByGroupId(groupId);
    }


    @Override
    public SubjectTeacherGroup update(SubjectTeacherGroup entity) throws AlreadyExistException {
        checkExistSubjectAndTeacher(entity.getTeacher().getSurname(),entity.getTeacher().getName(),
                entity.getTeacher().getPatronymic(),entity.getSubject().getNameSubject(),entity.getGroup().getId(),
                entity.getHours());

        return subjectForTeacherRepository.save(entity);
    }

    @Override
    public void delete(SubjectTeacherGroup entity) {
        subjectForTeacherRepository.delete(entity);
    }


    @Override
    @Transactional
    public void copyByInsert(Long target, Long source) {
        subjectForTeacherRepository.copyByInsert(target,source);
    }

    private void checkExistSubjectAndTeacher(String surname, String name, String
            patronymic, String subject, Long groupId,Integer hours) throws AlreadyExistException {

        SubjectTeacherGroup teacherGroup = subjectForTeacherRepository.findByFullNameAndSubject
                                                                    (surname,name,patronymic,subject,groupId,hours);
        if(teacherGroup != null){
            throw new AlreadyExistException("Такой преподаватель " +
                    "с таким же предметом в этой группу уже существует");
        }

    }

    @Override
    public List<SubjectTeacherGroup> findBySubjectNameAndFullName(Long Id, String start) {
        return subjectForTeacherRepository.findBySubjectNameAndFullName(Id,start);
    }

    @Override
    public List<SubjectTeacherGroup> readerCSV(String patch, Group group) throws Exception {
        List<SubjectTeacherGroup> list = new ArrayList<>();
        String delimiter = " ";
        List<Object> objectList = HandlerCSVUtil.importFile(patch, ';');

        for (int i = 0; i < objectList.size(); i++) {

            if (checkHeader((String[]) objectList.get(i))) {
                i++;
            }
            String[] strings = (String[]) objectList.get(i);
            SubjectTeacherGroup teacherGroup = new SubjectTeacherGroup();
            teacherGroup.setGroup(group);
            Subject subject = subjectService.findByNameSubject(strings[0]);
            if(subject == null){
                throw new NullPointerException
                        ("Предмета "+strings[0]+" не существует в базе данных, проверьте данные в csv файле");
            }
            String[] subString = strings[1].split(delimiter);
            String surname = subString[0];
            String name = subString[1];
            String patronymic = subString[2];
            Teacher teacher = teacherService.findBySurnameAndNameAndPatronymic(surname,name,patronymic);
            if(teacher == null){
                throw new NullPointerException
                        ("Преподавателя "+strings[1]+" не существует в базе данных, проверьте данные в csv файле");
            }
            if(strings[2] != null){
                teacherGroup.setHours(Integer.valueOf(strings[2]));
            }
            teacherGroup.setTeacher(teacher);
            teacherGroup.setSubject(subject);
            list.add(teacherGroup);
        }

        return list;
    }
    private boolean checkHeader(String[] line) {
        return line[0].equals("Предмет") || line[1].equals("Преподаватель");
    }

    @Override
    public List<SubjectTeacherGroup> findAllGroupAndSubjectByTeacherId(Long id) {
        return subjectForTeacherRepository.findAllGroupAndSubjectByTeacherId(id);
    }
}
