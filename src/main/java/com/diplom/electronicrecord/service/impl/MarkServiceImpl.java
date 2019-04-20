package com.diplom.electronicrecord.service.impl;

import com.diplom.electronicrecord.model.*;
import com.diplom.electronicrecord.repository.MarkRepository;
import com.diplom.electronicrecord.service.MarkService;
import com.diplom.electronicrecord.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    private int countLoserStudents;

    private int countAcceptableStudents;

    private int countGoodStudents;

    private int countExcellentStudent;

    private int countOther;

    private final StudentService studentService;

    @Autowired
    public MarkServiceImpl(MarkRepository markRepository, StudentService studentService) {
        this.markRepository = markRepository;
        this.studentService = studentService;
    }

    @Override
    public List<Marks> findMarksByDateAndGroupId(Date start, Date end, Long groupId) {
        resetNumber();
        List<Marks> marks = markRepository.findMarksByDateAndGroupId(start,end,groupId);
        findStudents(marks,groupId);
        return marks;
    }

    @Override
    public Integer getCountHonours() {
        return countExcellentStudent;
    }

    @Override
    public Integer getCountGoodStudents() {
        return countGoodStudents;
    }

    @Override
    public Integer getCountAcceptable() {
        return countAcceptableStudents;
    }

    @Override
    public Integer getCountBadStudents() {
        return countLoserStudents;
    }

    @Override
    public Integer getCountOther() {
        return countOther;
    }


    private void findStudents(List<Marks> list, Long groupId) {
        List<Integer[]> markStudents = new ArrayList<>();

        for (Student student : studentService.findAllStudentByGroupId(groupId)) {
            int indexMark = 0;
            Integer[] arrayMarks = new Integer[list.size()];
            for (int i = 0; i < list.size(); i++) {

                if (student.getId().equals(list.get(i).getStudent().getId())) {
                    arrayMarks[indexMark] = list.get(i).getMark();
                    indexMark++;
                }
            }
            markStudents.add(arrayMarks);
        }
        findMinMarks(markStudents);
    }

    private void findMinMarks(List<Integer[]> markStudents) {
        for (int i = 0; i < markStudents.size(); i++) {
            Integer[] array = markStudents.get(i);
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < array.length; j++) {
                if (array[j] != null) {
                    if (array[j] < min) {
                        min = array[j];
                    }
                }
            }
            countNumberOfRating(min);
        }
    }

    private void countNumberOfRating(int mark) {
        if (mark == 2) {
            countLoserStudents++;
        }
        if (mark == 3) {
            countAcceptableStudents++;
        }
        if (mark == 4) {
            countGoodStudents++;
        }
        if (mark == 5) {
            countExcellentStudent++;
        }
        if (mark < 2) {
            countOther++;
        }

    }

    private void resetNumber() {
        countAcceptableStudents = 0;
        countLoserStudents = 0;
        countGoodStudents = 0;
        countExcellentStudent = 0;
        countOther = 0;
    }

    @Override
    @Transactional
    public void insertMark(Statement statement, Long groupId) {
        markRepository.insertMars(statement.getId(),groupId);

        if(statement.getType().equals("Дипломная работа")){
            markRepository.insertDiplom(statement.getId());
        }

        if(statement.getType().equals("Курсовая работа")){
            markRepository.insertCourseWork(statement.getId());
        }
    }


    @Override
    public List<Marks> findMarksByStatementId(Long id) {
        return markRepository.findMarksByStatementId(id);
    }

    @Override
    public List<String> getHeaderCSV(Statement statement) {
        List<String> strings = new ArrayList<>();
        strings.add("№");
        strings.add("ФИО");
        strings.add("Оценка");

        if (statement.getType().equals("Курсовая работа")) {
            strings.add("Место прохождения практики");
            strings.add("Руководитель");
        }

        if (statement.getType().equals("Дипломная работа")) {
            strings.add("Тема");
        }
        return strings;
    }

    @Override
    public List<String[]> getValuesCSV(List<Marks> marksObservableList) {
        List<String[]> strings = new ArrayList<>();

        int size = 3;

        for (Marks mark : marksObservableList) {
            if (mark instanceof CourseWork) {
                size = 5;
            }
            if (mark instanceof Diplom) {
                size = 4;
            }

            String[] line = new String[size];
            line[0] = String.valueOf(mark.getNumber());
            line[1] = mark.getStudent().toString();
            line[2] = mark.getMarkString();

            if (mark instanceof CourseWork) {
                line[3] = ((CourseWork) mark).getPlacePractice();
                line[4] = ((CourseWork) mark).getFullNameBoss();
            }

            if (mark instanceof Diplom) {
                line[3] = ((Diplom) mark).getThesis();
            }

            strings.add(line);
        }
        return strings;
    }

    @Override
    @Transactional
    public void insertByGroupAndStatement(Statement statement, Long groupId) {
        markRepository.insertByGroupAndStatement(statement.getId(),groupId);

        if(statement.getType().equals("Дипломная работа")){
            markRepository.insertDiplomByStatementId(statement.getId());
        }

        if(statement.getType().equals("Курсовая работа")){
            markRepository.insertCourseWorkByStatementId(statement.getId());
        }
    }

    @Override
    public Marks update(Marks marks) {
        return markRepository.save(marks);

    }

    @Override
    public List<Marks> findMarksByStudentId(Long studentId, Long teacherId) {
        return markRepository.findMarksByStudentId(studentId,teacherId);
    }
}
