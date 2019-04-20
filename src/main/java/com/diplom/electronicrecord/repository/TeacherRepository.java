package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Teacher findBySurnameAndNameAndPatronymic( String surname, String name,String patr);

    @Query("FROM Teacher t WHERE CONCAT(t.name,t.surname,t.patronymic) LIKE %:start% ORDER BY t.surname")
    List<Teacher> findTeacherByLater(@Param("start") String start);
}
