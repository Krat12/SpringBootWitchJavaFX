package com.diplom.electronicrecord.util;

import com.jfoenix.controls.JFXTextField;
import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.*;
import java.util.Set;


public class ValidationUtil {

    private static final String numberMatcher = "^-?\\d+$";

    public static void textFieldIsDigit(JFXTextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches(numberMatcher)) {
                    field.setText(oldValue);
                } else {
                    try {
                        int value = Integer.parseInt(newValue);
                        field.setText(String.valueOf(value));
                    } catch (NumberFormatException e) {
                        field.setText(oldValue);
                    }
                }
            }
        });
    }


    public static boolean checkInputDataEntity(Object entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> violations = validator.validate(entity);

        if (violations.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String string){
        try {
            return string.trim().isEmpty();
        }catch (NullPointerException nfe) {
            return true;
        }
    }

}
