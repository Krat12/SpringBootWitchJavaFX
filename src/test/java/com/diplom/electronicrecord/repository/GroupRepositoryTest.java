package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Group;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    @Test
    public void testQueryFindByGroupName(){
        Group group = groupRepository.findByGroupNameAndYear("ПР15-04",2015);
        Assert.assertNotNull(group);
    }
    @Transactional
    @Test
    public void testQueryFindByGroupNameStartingWith(){
        List<Group> group = groupRepository.findByGroupNameStartingWith("П");
        Assert.assertNotNull(group);
    }
    @Transactional
    @Test
    public void  testQueryFindAllOrderByGroupName(){
        Assert.assertNotNull(groupRepository.findAll(Sort.by("groupName")));
    }
}
