package com.diplom.electronicrecord.service.impl;

import com.diplom.electronicrecord.model.*;
import com.diplom.electronicrecord.repository.StatementRepository;
import com.diplom.electronicrecord.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;

    @Autowired
    public StatementServiceImpl(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    @Override
    public List<Statement> findByCriteria(String type, Subject subject, Group group, Date startDate, Date endDate, String typeUser, Long teacherId) {
        return statementRepository.findByCriteria(type, subject, group, startDate, endDate, typeUser, teacherId);
    }


    @Override
    public Statement save(Statement statement) {
        return statementRepository.save(statement);
    }

    @Override
    public List<Statement> findStatementByTeacherId(Long teacherId) {
        return statementRepository.findStatementByTeacherId(teacherId);
    }
}
