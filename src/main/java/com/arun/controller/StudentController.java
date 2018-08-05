package com.arun.controller;

import com.arun.model.Student;
import com.arun.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Adwiti on 8/4/2018.
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students/v1/student/{streetId}/age/{age}")
    public ResponseEntity<List<Student>> getStudents(@PathVariable(value = "streetId") Integer streetId,
                                                     @PathVariable(value = "age") Integer age) {
        List<Student> students = studentService.getStudents(streetId, age);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/students/v1/student/name/{streetId}/age/{age}")
    public ResponseEntity<List<Student>> getStudentsByNamed(@PathVariable(value = "streetId") Integer streetId,
                                                     @PathVariable(value = "age") Integer age) {
        List<Student> students = studentService.getStudentsNamed(streetId, age);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

}
