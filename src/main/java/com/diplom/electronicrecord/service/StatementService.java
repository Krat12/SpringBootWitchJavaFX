package com.diplom.electronicrecord.service;

import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Marks;
import com.diplom.electronicrecord.model.Statement;
import com.diplom.electronicrecord.model.Subject;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StatementService {

    List<Statement> findByCriteria(String type, Subject subject, Group group, Date startDate, Date endDate, String typeUser, Long teacherId);

    Statement save(Statement statement);

    List<Statement> findStatementByTeacherId(Long teacherId,Date start,Date end);
}
