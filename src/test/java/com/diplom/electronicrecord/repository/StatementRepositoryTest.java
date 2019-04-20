package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Marks;
import com.diplom.electronicrecord.model.Statement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatementRepositoryTest {

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private GroupRepository groupRepository;


    @Transactional
    @Test
    public void testDynamicQuery(){
        Optional<Group> group  = groupRepository.findById((long) 32);

        List<Statement> statements = statementRepository.findByCriteria(null,null,group.get(),null,
                null,"", 0L);

    }
}
