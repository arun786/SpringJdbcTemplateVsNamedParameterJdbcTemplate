package com.arun.service;

import com.arun.dao.StudentDao;
import com.arun.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        studentDao.getCountOfList(streetId,age);
        return studentDao.getListOfStudent(streetId);
    }
}
