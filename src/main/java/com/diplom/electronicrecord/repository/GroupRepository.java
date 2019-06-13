package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {

    Group findByGroupNameAndYear(String name, int year);

    List<Group> findByGroupNameStartingWith(String start);

    List<Group> findByStatusOrderByGroupName(String status);

    List<Group> findByYearAndStatus(Integer year,String status);

    List<Group> findByYearLessThanEqualAndStatus(Integer year,String status);

    @Query("FROM Group AS g JOIN FETCH g.teacherGroups AS stg JOIN FETCH stg.teacher AS t" +
            " WHERE t.id = :id GROUP BY g.groupName ORDER BY g.groupName")
    List<Group>findGroupByTeacherId(@Param("id") Long id);

    @Query("FROM Group AS g JOIN FETCH g.teacherGroups AS stg JOIN FETCH stg.teacher AS t" +
            " WHERE t.id = :id AND g.groupName LIKE %:start%   GROUP BY g.groupName ORDER BY g.groupName")
    List<Group>findGroupByTeacherIdAndStartingWith(@Param("id") Long id,@Param("start") String start);

}
