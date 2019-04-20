package com.diplom.electronicrecord.service.impl;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Student;
import com.diplom.electronicrecord.model.Teacher;
import com.diplom.electronicrecord.model.User;
import com.diplom.electronicrecord.repository.TeacherRepository;
import com.diplom.electronicrecord.repository.UserRepository;
import com.diplom.electronicrecord.service.TeacherService;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import com.diplom.electronicrecord.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final UserRepository userRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, UserRepository userRepository) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Teacher save(Teacher entity) throws AlreadyExistException, ValidationException {
        validateInputDataTeacher(entity);
        checkExistTeacher(entity.getLogin(),entity.getPassword());
        return teacherRepository.save(entity);
    }

    @Override
    public Teacher update(Teacher entity) throws ValidationException, AlreadyExistException {
        validateInputDataTeacher(entity);
        Optional<Teacher> teacher = teacherRepository.findById(entity.getId());
        if(!teacher.get().equals(entity)) {
            checkExistTeacher(entity.getLogin(), entity.getPassword());
        }
        return teacherRepository.save(entity);
    }

    @Override
    public void delete(Teacher entity) {
        teacherRepository.delete(entity);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll(Sort.by("surname"));
    }

    @Override
    public Teacher findBySurnameAndNameAndPatronymic(String surname, String name, String patr) {
        return teacherRepository.findBySurnameAndNameAndPatronymic(surname,name,patr);
    }

    @Override
    public List<Teacher> findTeacherByLater(String start) {
        return teacherRepository.findTeacherByLater(start);
    }

    private void validateInputDataTeacher(Teacher teacher) {

        if (!ValidationUtil.checkInputDataEntity(teacher)) {
            throw new ValidationException("Данные не прошли провекру");
        }
    }

   private void checkExistTeacher(String login, String password) throws AlreadyExistException {
        User user = userRepository.findByLoginAndPassword(login, password);
        if (user != null) {
            throw new AlreadyExistException("Пользователь с логином: "+login +" и паролем: "+password+" уже существует");
        }
    }

    public List<String> getHeaderCSV() {
        List<String> strings = new ArrayList<>();
        strings.add("ФИО");
        strings.add("Категория");
        strings.add("Логин");
        strings.add("Пароль");
        return strings;
    }

    public List<String[]> getValuesCSV(List<Teacher> teachers) {
        List<String[]> strings = new ArrayList<>();
        for (Teacher teacher : teachers) {
            String[] line = new String[4];
            line[0] = teacher.toString();
            line[1] = teacher.getCategory();
            line[2] = teacher.getLogin();
            line[3] = teacher.getPassword();
            strings.add(line);
        }
        return strings;
    }

    @Override
    public List<Teacher> rederCSV(String patch) throws Exception {
        String delimiter = " ";
        List<Teacher> saveTeacher = new ArrayList<>();
        List<Object> objectList = HandlerCSVUtil.importFile(patch, ';');
        for (int i = 0; i < objectList.size(); i++) {

            if (checkHeader((String[]) objectList.get(i))) {
                i++;
            }
            String[] strings = (String[]) objectList.get(i);
            Teacher teacher = new Teacher();

                String[] subString = strings[0].split(delimiter);
                teacher.setName(subString[1]);
                teacher.setSurname(subString[0]);
                teacher.setPatronymic(subString[1]);
                teacher.setCategory(strings[1]);
                if (strings.length > 2) {
                    teacher.setLogin(strings[2]);
                    teacher.setPassword(strings[3]);
                }
                saveTeacher.add(teacher);
        }
        return saveTeacher;
    }

    private boolean checkHeader(String[] line) {
        if (line[0].equals("ФИО")) {
            return true;
        } else if (line[1].equals("Категория") || line[1].equals("Логин")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveAll(List<Teacher> teacherList) throws AlreadyExistException,ValidationException {
        for (Teacher teacher : teacherList) {
            validateInputDataTeacher(teacher);
            checkExistTeacher(teacher.getLogin(),teacher.getPassword());
        }

        teacherRepository.saveAll(teacherList);
    }

    @Override
    public Teacher findTeacherById(Long id) {
        return teacherRepository.findById(id).get();
    }
}
