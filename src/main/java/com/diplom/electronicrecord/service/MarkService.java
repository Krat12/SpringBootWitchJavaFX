package com.diplom.electronicrecord.service;

import com.diplom.electronicrecord.model.Marks;
import com.diplom.electronicrecord.model.Statement;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MarkService {

    List<Marks>findMarksByDateAndGroupId(Date start,Date end,Long groupId);

    Integer getCountHonours();

    Integer getCountGoodStudents();

    Integer getCountAcceptable();

    Integer getCountBadStudents();

    Integer getCountOther();

    void insertMark(Statement statement, Long groupId);

    List<Marks> findMarksByStatementId(Long id);

    List<String> getHeaderCSV(Statement statement);

    List<String[]> getValuesCSV(List<Marks> marksObservableList);

    void insertByGroupAndStatement(Statement statement, Long groupId);

    Marks update(Marks marks);

    List<Marks> findMarksByStudentId(Long studentId, Long teacherId);
    
}
