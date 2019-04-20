package com.diplom.electronicrecord.service.impl;

import com.diplom.electronicrecord.exeption.AlreadyExistException;
import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Student;
import com.diplom.electronicrecord.model.User;
import com.diplom.electronicrecord.repository.StudentRepository;
import com.diplom.electronicrecord.repository.UserRepository;
import com.diplom.electronicrecord.service.StudentService;
import com.diplom.electronicrecord.util.HandlerCSVUtil;
import com.diplom.electronicrecord.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentImpl implements StudentService {


    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    @Autowired
    public StudentImpl(StudentRepository studentRepository,
                       UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Student save(Student entity) throws AlreadyExistException, ValidationException {

        validateInputDataStudent(entity);
        checkExistStudent(entity.getLogin(), entity.getPassword());

        return studentRepository.save(entity);
    }

    @Override
    public Student update(Student entity) throws ValidationException, AlreadyExistException {
        validateInputDataStudent(entity);
        Optional<Student> student = studentRepository.findById(entity.getId());
        if(!student.get().equals(entity)){
            checkExistStudent(entity.getLogin(),entity.getPassword());
        }
        return studentRepository.save(entity);
    }

    @Override
    public void delete(Student entity) {
        studentRepository.delete(entity);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll(Sort.by("surname"));
    }

    @Override
    public List<Student> findAllStudentByGroupId(Long id) {
        return studentRepository.findAllStudentByGroupId(id);
    }

    @Override
    public List<Student> findAllStudentByGroupIdAndFullName(Long id, String name) {
        return studentRepository.findAllStudentByGroupIdAndFullName(id, name);
    }

    private void validateInputDataStudent(Student student) {

        if (ValidationUtil.checkInputDataEntity(student)) {
            if (student.getGroup() == null) {
                throw new ValidationException("Данные не прошли провекру");
            }
        } else {
            throw new ValidationException("Данные не прошли провекру");
        }
    }

    public void checkExistStudent(String login, String password) throws AlreadyExistException {
        User user = userRepository.findByLoginAndPassword(login, password);
        if (user != null) {
            throw new AlreadyExistException("Пользователь с логином: "+login +" и паролем: "+password+" уже существует");
        }
    }

    @Override
    public void saveAll(List<Student> studentList) throws ValidationException, AlreadyExistException {
        for (Student student : studentList) {
            ValidationUtil.checkInputDataEntity(student);
            checkExistStudent(student.getLogin(),student.getPassword());
        }
        studentRepository.saveAll(studentList);
    }

    public List<Student> readerCSV(String patch, Group group, int size) throws Exception {
        String delimiter = " ";
        int numberStudent = size + 1;
        List<Student> saveStudents = new ArrayList<>();
        List<Object> objectList = HandlerCSVUtil.importFile(patch, ';');
        for (int i = 0; i < objectList.size(); i++) {

            if (checkHeader((String[]) objectList.get(i))) {
                i++;
            }
            String[] strings = (String[]) objectList.get(i);
            Student student = new Student();
            student.setGroup(group);

            if (ValidationUtil.isNumeric(strings[0])) {
                student.setFullName(strings[1]);

                String[] subString = strings[1].split(delimiter);
                student.setName(subString[1]);
                student.setSurname(subString[0]);
                student.setPatronymic(subString[2]);

                student.setNumberBook(Integer.valueOf(strings[2]));
                student.setNumberStudent(numberStudent++);

                if (strings.length > 3) {
                    student.setLogin(strings[3]);
                    student.setPassword(strings[4]);
                }
                saveStudents.add(student);
            } else {
                student.setFullName(strings[0]);
                String[] subString = strings[0].split(delimiter);
                student.setName(subString[1]);
                student.setSurname(subString[0]);
                student.setPatronymic(subString[2]);
                student.setNumberBook(Integer.valueOf(strings[1]));
                student.setNumberStudent(numberStudent++);
                if (strings.length > 2) {
                    student.setLogin(strings[2]);
                    student.setPassword(strings[3]);
                }
                saveStudents.add(student);
            }

        }
        return saveStudents;
    }

    private boolean checkHeader(String[] line) {
        if (line[0].equals("№")) {
            return true;
        } else if (line[0].equals("ФИО")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Student> findByGroupIsNullOrderBySurname() {
        return studentRepository.findByGroupIsNullOrderBySurname();
    }

    @Override
    public void saveNoCheck(Student student) {
        studentRepository.save(student);
    }


    @Override
    public List<Student> findStudentsByTeacherId(Long teacherId) {
        return studentRepository.findStudentsByTeacherId(teacherId);
    }

    @Override
    public List<Student> findStudentsByTeacherIdAndStartWitch(Long teacherId, String start) {
        return studentRepository.findStudentsByTeacherIdAndStartWitch(teacherId,start);
    }
}
