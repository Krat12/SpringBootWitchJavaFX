package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Marks;
import com.diplom.electronicrecord.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MarkRepository extends JpaRepository<Marks,Long> {

    @Query("FROM Marks m JOIN FETCH m.student JOIN FETCH m.statement st JOIN FETCH st.group g " +
            " WHERE g.id = :id AND st.date BETWEEN :start AND :end ")
    List<Marks> findMarksByDateAndGroupId(@Param("start") Date start,@Param("end") Date end, @Param("id")Long groupId);

    @Modifying
    @Query(value = "INSERT INTO marks (statement_id,student_id) " +
            "(SELECT ?1, A.id FROM student as A where A.Group_id = ?2);"
            ,nativeQuery = true)
    void insertMars(Long statementId, Long groupId);

    @Modifying
    @Query(value = "INSERT INTO diplom (id) " +
            "(SELECT A.id FROM marks as A where A.statement_id = ?1);"
            ,nativeQuery = true)
    void insertDiplom(Long statementId);

    @Modifying
    @Query(value = "INSERT INTO marks (statement_id,student_id) " +
            "(SELECT ?1, s.id FROM student as s where s.id " +
            "not in (select r.student_id from marks r where r.statement_id = ?1 ) and s.Group_id = ?2);"
            ,nativeQuery = true)
    void insertByGroupAndStatement(Long statementId, Long groupId);

    @Modifying
    @Query(value = "INSERT INTO diplom (id) " +
            "(SELECT m.id FROM marks as m inner join statement as s on s.id = m.statement_id where m.id " +
            "not in (select r.id from diplom r ) and s.id = ?1);"
            ,nativeQuery = true)
    void insertDiplomByStatementId(Long statementId);

    @Query("FROM Marks m JOIN FETCH m.statement st JOIN FETCH m.student WHERE st.id = :id")
    List<Marks> findMarksByStatementId(@Param("id") Long id);


    @Modifying
    @Query(value = "INSERT INTO course_work (id) " +
            "(SELECT A.id FROM marks as A where A.statement_id = ?1);"
            ,nativeQuery = true)
    void insertCourseWork(Long statementId);


    @Modifying
    @Query(value = "INSERT INTO course_work (id) " +
            "(SELECT m.id FROM marks as m inner join statement as s on s.id = m.statement_id where m.id " +
            "not in (select r.id from course_work r ) and s.id = ?1);"
            ,nativeQuery = true)
    void insertCourseWorkByStatementId(Long statementId);

    @Query("FROM  Marks m JOIN FETCH m.student s JOIN FETCH m.statement st JOIN FETCH st.subject " +
            "JOIN FETCH st.teacher t WHERE s.id = :id AND t.id = :teacherId")
    List<Marks> findMarksByStudentId(@Param("id") Long studentId,@Param("teacherId") Long teacherId);
}
