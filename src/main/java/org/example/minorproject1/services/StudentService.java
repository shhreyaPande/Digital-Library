package org.example.minorproject1.services;

import org.example.minorproject1.dtos.CreateStudentRequest;
import org.example.minorproject1.models.Student;
import org.example.minorproject1.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student create(CreateStudentRequest createStudentRequest){

        Student student = createStudentRequest.convertToStudent();
        return this.studentRepository.save(student);
    }

    public Student findByRollNumber(String rollNo){
        return this.studentRepository.findByRollNumber(rollNo);
    }
}
