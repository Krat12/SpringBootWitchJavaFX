package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Teacher;
import com.diplom.electronicrecord.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByLoginAndPassword(String login, String password);




}
