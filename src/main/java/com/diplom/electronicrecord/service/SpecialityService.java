package com.diplom.electronicrecord.service;

import com.diplom.electronicrecord.model.Speciality;

import java.util.List;

public interface SpecialityService extends GenericService<Speciality>{

    Speciality findSpecialityByGroup(String name);

    Speciality findByNameSpeciality(String name);

    List<String[]> getValuesCSV(List<Speciality> specialities);

    List<String>getHeaderCSV();

    List<Speciality> findByNameSpecialityStartingWith(String start);

    List<Speciality> readerCSV(String patch) throws Exception;

    void saveAll(List<Speciality> specialities);
}
