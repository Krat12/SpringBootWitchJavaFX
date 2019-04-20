package com.diplom.electronicrecord.util.validate;

import com.diplom.electronicrecord.model.User;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.assertEquals;


public class ValidateDataUserTest {

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkDataIsNull(){
        User user = new User();
        user.setLogin(null);
        user.setPassword("31231");
        user.setName("Test");
        user.setSurname("TestSurname");
        user.setPatronymic("Test");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1,violations.size());
    }

    @Test
    public void checkDataSizeMin(){
        User user = new User();
        user.setLogin("Testing");
        user.setPassword("");
        user.setName("Test");
        user.setSurname("TestSurname");
        user.setPatronymic("Test");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1,violations.size());
    }
}
