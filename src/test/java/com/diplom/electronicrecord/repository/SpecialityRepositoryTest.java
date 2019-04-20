package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Speciality;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpecialityRepositoryTest {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Transactional
    @Test
    public void testQueryFindByGroupName(){
        Speciality speciality = specialityRepository.findByGroupName("ПР15-04");
        Assert.assertNotNull(speciality);
    }
    @Transactional
    @Test
    public void testQueryFindByNameSpeciality(){
        Speciality speciality = specialityRepository.findByNameSpeciality("dadada");
        Assert.assertNotNull(speciality);
    }

}
