package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Speciality;
import com.diplom.electronicrecord.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality,Long> {

    @Query("from Speciality s join fetch s.groups g where g.groupName = :name ")
    Speciality findByGroupName(@Param("name") String name);

    Speciality findByNameSpeciality(String name);

    List<Speciality> findByNameSpecialityStartingWithOrderByNameSpeciality(String start);
}
