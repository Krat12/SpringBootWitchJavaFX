package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {

    Subject findByNameSubject(String name);

    Subject findByNameSubjectStartingWith(String start);

    @Query("FROM Subject AS s JOIN FETCH s.teacherGroups AS stg JOIN FETCH stg.teacher AS t JOIN FETCH stg.group AS g " +
            " WHERE t.id = :teacherId AND g.id = :groupId")
    List<Subject> findSubjectsByGroupAndTeacher(@Param("groupId") Long groupId,
                                               @Param("teacherId") Long teacherId);
}
