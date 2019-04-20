package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.SubjectTeacherGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectTeacherGroupRepository extends JpaRepository<SubjectTeacherGroup,Long> {

    @Query("FROM SubjectTeacherGroup AS stg JOIN FETCH  stg.teacher JOIN FETCH  stg.group AS gr " +
            "JOIN FETCH stg.subject WHERE gr.id = :id")
    List<SubjectTeacherGroup> findAllTeacherAndSubjectByGroupId(@Param("id") Long id);

    @Modifying
    @Query(value = "INSERT INTO subject_teacher_group (group_id,subject_id,teacher_id) " +
            "(SELECT ?1, A.subject_id,A.teacher_id FROM subject_teacher_group as A where A.group_id = ?2)"
            ,nativeQuery = true)
    void copyByInsert(Long target, Long source);

    @Query("FROM SubjectTeacherGroup AS stg JOIN FETCH  stg.teacher AS t JOIN FETCH  stg.group AS gr " +
            "JOIN FETCH stg.subject AS s WHERE gr.id = :id AND t.name = :name AND t.surname = :surname" +
            " AND t.patronymic = :patr AND s.nameSubject = :subject AND stg.hours = :hours")
    SubjectTeacherGroup findByFullNameAndSubject(@Param("surname") String surname,
                                                 @Param("name")String name,
                                                 @Param("patr")String patronymic,String subject,
                                                 @Param("id") Long groupId,
                                                 @Param("hours") Integer hours);

    @Query("FROM SubjectTeacherGroup AS stg JOIN FETCH  stg.teacher AS t JOIN FETCH  stg.group AS gr " +
            "JOIN FETCH stg.subject AS s WHERE gr.id = :id AND CONCAT(t.name,t.surname,t.patronymic,s.nameSubject) " +
            "LIKE %:start% ORDER BY t.surname")
    List<SubjectTeacherGroup> findBySubjectNameAndFullName(@Param("id") Long id,@Param("start") String start);


    @Query("FROM SubjectTeacherGroup AS stg JOIN FETCH  stg.teacher AS t JOIN FETCH  stg.group AS gr " +
            "JOIN FETCH stg.subject WHERE t.id = :id")
    List<SubjectTeacherGroup> findAllGroupAndSubjectByTeacherId(@Param("id") Long id);
}
