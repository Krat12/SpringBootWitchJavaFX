package com.diplom.electronicrecord.service.impl;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Speciality;
import com.diplom.electronicrecord.model.Subject;
import com.diplom.electronicrecord.repository.SpecialityRepository;
import com.diplom.electronicrecord.service.SpecialityService;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import com.diplom.electronicrecord.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.validation.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialityServiceImpl implements SpecialityService {


    private SpecialityRepository specialityRepository;

    @Autowired
    public SpecialityServiceImpl(SpecialityRepository specialityRepository) {
        this.specialityRepository =specialityRepository;
    }

    @Override
    public Speciality save(Speciality speciality) throws ValidationException, AlreadyExistException {
        return saveOrUpdate(speciality);
    }

    @Override
    public Speciality update(Speciality speciality) throws ValidationException, AlreadyExistException {
        return saveOrUpdate(speciality);
    }

    private Speciality saveOrUpdate(Speciality speciality) throws ValidationException, AlreadyExistException  {
        validateInputDataSpeciality(speciality);
        Speciality checkSpec = findByNameSpeciality(speciality.getNameSpeciality());
        if(checkSpec != null){
            throw  new AlreadyExistException("Специальность "+speciality.getNameSpeciality()+" уже существует");
        }
        return specialityRepository.save(speciality);
    }

    @Override
    public void delete(Speciality speciality) {
        specialityRepository.delete(speciality);
    }

    @Override
    public List<Speciality> findAll() {
        return specialityRepository.findAll(Sort.by("nameSpeciality"));
    }

    @Override
    public Speciality findSpecialityByGroup(String name) {
        return specialityRepository.findByGroupName(name);
    }

    @Override
    public Speciality findByNameSpeciality(String name) {
        return specialityRepository.findByNameSpeciality(name);
    }


    private void validateInputDataSpeciality(Speciality speciality) {

        if (!ValidationUtil.checkInputDataEntity(speciality)) {
            throw new ValidationException("Данные не прошли провекру");
        }
    }

    @Override
    public List<String[]> getValuesCSV(List<Speciality> specialities) {
        List<String[]> stringsList = new ArrayList<>();
        for (Speciality speciality : specialities) {
            String[] line = new String[1];
            line[0] = speciality.getNameSpeciality();
            stringsList.add(line);
        }

        return stringsList;
    }

    @Override
    public List<String> getHeaderCSV() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Cпециальность");
        return stringList;
    }

    @Override
    public List<Speciality> findByNameSpecialityStartingWith(String start) {
        return specialityRepository.findByNameSpecialityStartingWithOrderByNameSpeciality(start);
    }

    @Override
    public List<Speciality> readerCSV(String patch) throws Exception {
        List<Speciality> specialities = new ArrayList<>();
        List<Object> objectList = HandlerCSVUtil.importFile(patch, ';');
        for (int i = 0; i < objectList.size(); i++) {
            if (checkHeader((String[]) objectList.get(i))) {
                i++;
            }
            String[] strings = (String[]) objectList.get(i);
            Speciality speciality = new Speciality();
            speciality.setNameSpeciality(strings[0]);
            specialities.add(speciality);
        }
        return specialities;

    }
    private boolean checkHeader(String[] line) {
        if (line[0].equals("Cпециальность") || line[0].equals("Специальности")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveAll(List<Speciality> specialities) {
        specialityRepository.saveAll(specialities);
    }
}
