package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Subject;

import java.util.Date;
import java.util.List;

public interface StatementRepositoryCustom<T> {
    List<T> findByCriteria(String type, Subject subject, Group group, Date startDate, Date endDate, String typeUser, Long teacherId);
}
