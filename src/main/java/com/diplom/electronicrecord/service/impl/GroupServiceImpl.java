package com.diplom.electronicrecord.service.impl;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Speciality;
import com.diplom.electronicrecord.repository.GroupRepository;
import com.diplom.electronicrecord.service.GroupService;
import com.diplom.electronicrecord.service.SpecialityService;
import com.diplom.electronicrecord.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.List;


@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final SpecialityService specialityService;


    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, SpecialityService specialityService) {
        this.groupRepository = groupRepository;
        this.specialityService = specialityService;
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findByStatusOrderByGroupName("обучаются");
    }

    @Override
    public Group save(Group group) throws ValidationException, AlreadyExistException {

        validateInputDataGroup(group);
        checkExistGroup(group.getGroupName(), group.getYear());
        if (group.getSpeciality().getId() == null) {
            Speciality newSpec = specialityService.save(group.getSpeciality());
            group.setSpeciality(newSpec);
            return groupRepository.save(group);
        }else {
            return groupRepository.save(group);
        }

    }

    @Override
    public void delete(Group group) {
        groupRepository.delete(group);
    }


    @Override
    public Group update(Group group) throws ValidationException,AlreadyExistException {
          validateInputDataGroup(group);
        if (group.getSpeciality().getId() == null) {
            Speciality newSpec = specialityService.save(group.getSpeciality());
            group.setSpeciality(newSpec);
            return groupRepository.save(group);
        }

        return groupRepository.save(group);
    }


    private void validateInputDataGroup(Group group) {

        if (ValidationUtil.checkInputDataEntity(group)) {
            if (group.getSpeciality().getNameSpeciality().trim().isEmpty()) {
                throw new ValidationException("Данные не прошли провекру");
            }
        } else {
            throw new ValidationException("Данные не прошли провекру");
        }
    }


    @Override
    public Group findByGroupNameYear(String name, int year) {
        return groupRepository.findByGroupNameAndYear(name, year);
    }

    private void checkExistGroup(String name, int year) throws AlreadyExistException {
        Group checkExistGroup = findByGroupNameYear(name, year);
        if (checkExistGroup != null) {
            throw new AlreadyExistException("Такая группа уже существует");
        }
    }

    @Override
    public List<Group> findByGroupNameStartingWith(String start) {
        return groupRepository.findByGroupNameStartingWith(start);
    }

    @Override
    public List<Group> findByYearAndStatus(Integer year, String status) {
        return groupRepository.findByYearAndStatus(year,status);
    }

    @Override
    public List<Group> findByYearLessThanEqualAndStatus(Integer year, String status) {
        return groupRepository.findByYearLessThanEqualAndStatus(year,status);
    }

    @Override
    public List<Group> findGroupByTeacherId(Long id) {
        return groupRepository.findGroupByTeacherId(id);
    }


    @Override
    public List<Group> findGroupByTeacherIdAndStartingWith(Long id, String start) {
        return groupRepository.findGroupByTeacherIdAndStartingWith(id,start);
    }
}
