package org.example.minorproject1.repositories;

import org.example.minorproject1.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository <Student, Integer> {

    Student findByRollNumber(String rollNumber);
}
