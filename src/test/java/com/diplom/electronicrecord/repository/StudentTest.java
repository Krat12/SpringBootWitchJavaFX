package com.diplom.electronicrecord.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testQueryIsWork(){
        System.out.println(studentRepository.findByGroupIsNullOrderBySurname());
    }
}
