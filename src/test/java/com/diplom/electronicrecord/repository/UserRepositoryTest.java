package com.diplom.electronicrecord.repository;

import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Student;
import com.diplom.electronicrecord.model.Teacher;
import com.diplom.electronicrecord.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private static User user;

    @BeforeClass
    public static void initUser(){
        user = new User();
        user.setLogin("test");
        user.setPassword("test1");
        user.setName("Test");
        user.setSurname("Test");
        user.setPatronymic("User");
    }
    @Transactional
    @Test
    public void findUserByLoginAndTest(){
        userRepository.save(user);
        User found = userRepository.findByLoginAndPassword(user.getLogin(),user.getPassword());
        Assertions.assertThat(found.getLogin())
                .isEqualTo(user.getLogin());
    }

    @Transactional
    @Test
    public void checkInsertNewUser(){
        Student student = new Student();
        student.setNumberBook(231);

        Optional<Group>group =groupRepository.findById((long) 32);
        student.setGroup(group.get());

        student.setLogin("testing");
        student.setPassword("312342");
        student.setName("Test");
        student.setSurname("Test");
        student.setPatronymic("User");
        userRepository.save(student);
    }

    @Transactional
    @Test
    public void checkFindTeacherByFullName(){
        Teacher teacher = teacherRepository.findBySurnameAndNameAndPatronymic("dadadsa","Игорь","Игорь");
        Assert.assertNotNull(teacher);

    }



}