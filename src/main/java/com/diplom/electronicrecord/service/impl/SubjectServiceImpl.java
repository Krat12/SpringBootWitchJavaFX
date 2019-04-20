package com.diplom.electronicrecord.service.impl;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Student;
import com.diplom.electronicrecord.model.Subject;
import com.diplom.electronicrecord.model.Teacher;
import com.diplom.electronicrecord.model.User;
import com.diplom.electronicrecord.repository.SubjectRepository;
import com.diplom.electronicrecord.service.SubjectService;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import com.diplom.electronicrecord.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject save(Subject entity) throws AlreadyExistException, ValidationException {
        validateInputDataTeacher(entity);
        checkExistSubject(entity.getNameSubject());
        return subjectRepository.save(entity);
    }

    @Override
    public Subject update(Subject entity) throws ValidationException, AlreadyExistException {
        validateInputDataTeacher(entity);
        checkExistSubject(entity.getNameSubject());
        return subjectRepository.save(entity);
    }

    @Override
    public void delete(Subject entity) {
        subjectRepository.delete(entity);
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll(Sort.by("nameSubject"));
    }

    @Override
    public Subject findByNameSubject(String name) {
        return subjectRepository.findByNameSubject(name);
    }

    private void validateInputDataTeacher(Subject subject) {

        if (!ValidationUtil.checkInputDataEntity(subject)) {
            throw new ValidationException("Данные не прошли провекру");
        }
    }

    public void checkExistSubject(String name) throws AlreadyExistException {
        Subject subject = subjectRepository.findByNameSubject(name);
        if (subject != null) {
            throw new AlreadyExistException("Предмет "+subject.getNameSubject()+" уже существует");
        }
    }

    @Override
    public Subject findByNameSubjectStartingWith(String start) {
        return subjectRepository.findByNameSubjectStartingWith(start);
    }

    @Override
    public List<String[]> getValuesCSV(List<Subject> subjects) {
        List<String[]> stringsList = new ArrayList<>();
        for (Subject subject : subjects) {
            String[] line = new String[1];
            line[0] = subject.getNameSubject();
            stringsList.add(line);
        }

        return stringsList;
    }

    @Override
    public List<String> getHeaderCSV() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Предмет");
        return stringList;
    }

    @Override
    public List<Subject> readerCSV(String patch) throws Exception {
        List<Subject> subjects = new ArrayList<>();
        List<Object> objectList = HandlerCSVUtil.importFile(patch, ';');
        for (int i = 0; i < objectList.size(); i++) {
            if (checkHeader((String[]) objectList.get(i))) {
                i++;
            }
            String[] strings = (String[]) objectList.get(i);
            Subject subject = new Subject();
            subject.setNameSubject(strings[0]);
            subjects.add(subject);
        }
        return subjects;
    }

    @Override
    public void saveAllSubject(List<Subject> subjects) {
        subjectRepository.saveAll(subjects);
    }

    private boolean checkHeader(String[] line) {
        if (line[0].equals("Предмет")) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Subject> findSubjectsByGroupAndTeacher(Long groupId, Long teacherId) {
        return subjectRepository.findSubjectsByGroupAndTeacher(groupId,teacherId);
    }
}
