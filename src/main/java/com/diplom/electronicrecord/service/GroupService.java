package com.diplom.electronicrecord.service;


import com.diplom.electronicrecord.model.Group;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupService extends GenericService<Group> {

    Group findByGroupNameYear(String name, int year);

    List<Group> findByGroupNameStartingWith(String start);

    List<Group> findByYearAndStatus(Integer year, String status);

    List<Group> findByYearLessThanEqualAndStatus(Integer year,String status);

    List<Group>findGroupByTeacherId(Long id);

    List<Group>findGroupByTeacherIdAndStartingWith(Long id,String start);

}
