package com.arun.service;

import com.arun.model.Student;

import java.util.List;

/**
 * Created by Adwiti on 8/4/2018.
 */
public interface StudentService {
    List<Student> getStudents(Integer streetId, Integer age);
}