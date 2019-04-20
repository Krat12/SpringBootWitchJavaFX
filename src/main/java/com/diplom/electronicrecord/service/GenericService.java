package com.diplom.electronicrecord.service;

import com.diplom.electronicrecord.exeption.AlreadyExistException;

import javax.validation.ValidationException;
import java.util.List;

public interface GenericService<T extends Object> {

    T save(T entity) throws AlreadyExistException, ValidationException;

    T update(T entity) throws ValidationException, AlreadyExistException;

    void delete(T entity);

    List<T> findAll();
}
