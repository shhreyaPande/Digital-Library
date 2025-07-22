package org.example.minorproject1.controllers;

import org.example.minorproject1.dtos.CreateStudentRequest;
import org.example.minorproject1.models.Student;
import org.example.minorproject1.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    private static Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @PostMapping("")
    public Student create(@RequestBody CreateStudentRequest createStudentRequest){
        return studentService.create(createStudentRequest);
    }
}
