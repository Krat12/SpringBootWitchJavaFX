package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("FROM Student s join fetch s.group g where g.id = :id ORDER BY s.surname")
    List<Student> findAllStudentByGroupId(@Param("id") Long id);

    @Query("FROM Student s join fetch s.group g where g.id = :id " +
            "AND CONCAT(s.surname,' ',s.name,' ',s.patronymic) LIKE %:start% ORDER BY s.surname")
    List<Student> findAllStudentByGroupIdAndFullName(@Param("id") Long id, @Param("start") String start);

    List<Student>findByGroupIsNullOrderBySurname();

    @Query("SELECT  DISTINCT s FROM Student s JOIN FETCH s.group AS g JOIN FETCH g.teacherGroups stg JOIN FETCH stg.teacher t " +
            "WHERE t.id = :id ORDER BY s.surname")
    List<Student> findStudentsByTeacherId(@Param("id") Long teacherId);

    @Query("SELECT  DISTINCT s FROM Student s JOIN FETCH s.group AS g JOIN FETCH g.teacherGroups stg JOIN FETCH stg.teacher t " +
            "WHERE t.id = :id AND  CONCAT(s.surname ,s.name,s.patronymic) like %:start% ORDER BY s.surname")
    List<Student> findStudentsByTeacherIdAndStartWitch(@Param("id") Long teacherId,@Param("start") String start);

}
