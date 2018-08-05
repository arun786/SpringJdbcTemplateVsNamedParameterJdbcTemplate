package com.arun.service;

import com.arun.dao.StudentDao;
import com.arun.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Adwiti on 8/4/2018.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Student> getStudents(Integer streetId, Integer age) {
        Integer countOfList = studentDao.getCountOfList(streetId, age);
        if (countOfList == 0) {
            return Arrays.asList();
        }
        return studentDao.getListOfStudent(streetId, age);
    }

    @Override
    public List<Student> getStudentsNamed(Integer streetId, Integer age) {
        Integer countOfListWithNamed = studentDao.getCountOfListWithNamed(streetId, age);
        if (countOfListWithNamed == 0) {
            return Arrays.asList();
        }
        return studentDao.getListOfStudentWithNamed(streetId,age);
    }
}
