package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Statement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StatementRepository extends CrudRepository<Statement, Long>,StatementRepositoryCustom<Statement>  {

    @Query("FROM Statement st JOIN FETCH st.teacher t JOIN FETCH st.group JOIN FETCH st.subject " +
            "WHERE t.id = :teacherId AND st.date BETWEEN :start AND :endDate AND st.type <> 'Зачет' ")
    List<Statement> findStatementByTeacherId(@Param("teacherId") Long teacherId,
                                             @Param("start") Date start,
                                             @Param("endDate")Date end);
}
