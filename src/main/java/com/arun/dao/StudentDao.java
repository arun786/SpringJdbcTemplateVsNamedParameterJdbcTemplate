package com.arun.dao;

import com.arun.model.Student;

import java.util.List;

/**
 * Created by Adwiti on 8/4/2018.
 */
public interface StudentDao {
    Integer getCountOfList(Integer streetId, Integer age);
    Integer getCountOfListWithNamed(Integer streetId, Integer age);
    List<Student> getListOfStudent(Integer streetId, Integer age);
    List<Student> getListOfStudentWithNamed(Integer streetId, Integer age);
}
