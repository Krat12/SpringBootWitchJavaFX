package com.diplom.electronicrecord.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SubjectFroTeacherRepository {

    @Autowired
    private SubjectTeacherGroupRepository subjectForTeacher;

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    @Test
    public void testQuery(){

        subjectForTeacher.copyByInsert((long) 33,(long) 32);
    }


}
