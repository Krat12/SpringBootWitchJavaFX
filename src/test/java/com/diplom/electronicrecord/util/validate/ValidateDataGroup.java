package com.diplom.electronicrecord.util.validate;

import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Speciality;
import com.diplom.electronicrecord.util.ValidationUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.*;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ValidateDataGroup {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void checkDataIsNull(){
        Group group = new Group();
        group.setSpeciality(null);
        group.setGroupName(null);
        group.setYear(null);
        Set<ConstraintViolation<Group>> violations = validator.validate(group);
        assertEquals(3,violations.size());
    }

    @Test
    public void checkGroupYear(){
        Group group = new Group();
        group.setSpeciality(new Speciality("PR"));
        group.setGroupName("PR15");
        group.setYear(2);
        Set<ConstraintViolation<Group>> violations = validator.validate(group);
        assertEquals(1,violations.size());
    }

    @Test
    public void checkGroupName(){
        Group group = new Group();
        group.setSpeciality(new Speciality("PR"));
        group.setGroupName("");
        group.setYear(2015);
        Set<ConstraintViolation<Group>> violations = validator.validate(group);
        assertEquals(1,violations.size());
    }

    @Test()
    public void checkExceptionValidate(){
        Group group = new Group();
        group.setSpeciality(null);
        group.setGroupName(null);
        group.setYear(null);
        Assert.assertFalse(ValidationUtil.checkInputDataEntity(group));
        Speciality speciality = new Speciality("Test");
        group.setSpeciality(speciality);
        group.setGroupName("Test");
        group.setYear(2015);
        Assert.assertTrue(ValidationUtil.checkInputDataEntity(group));


    }
}
