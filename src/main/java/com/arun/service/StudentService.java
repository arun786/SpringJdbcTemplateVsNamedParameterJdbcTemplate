package com.arun.service;

import com.arun.model.Account;
import com.arun.model.Student;

import java.util.List;

/**
 * Created by Adwiti on 8/4/2018.
 */
public interface StudentService {
    List<Student> getStudents(Integer streetId, Integer age);
    List<Student> getStudentsNamed(Integer streetId, Integer age);
    List<Account> getAccounts(Integer id);
}
