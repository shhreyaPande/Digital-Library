package org.example.minorproject1;

import org.example.minorproject1.models.*;
import org.example.minorproject1.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinorProject1Application {  // implements CommandLineRunner {

//    @Autowired
//    TransactionRepository transactionRepository;

    public static void main(String[] args) {
        SpringApplication.run(MinorProject1Application.class, args);

    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        Student student = new Student();
//        Book book = new Book();
//        student.setId(1);
//        book.setId(3);
//        Transaction t = transactionRepository.
//                findTopByBookAndStudentAndTypeAndStatusOrderByIdDesc
//                        (book, student, TransactionType.ISSUE, TransactionStatus.SUCCESS);
//
//        System.out.println(t);
//    }
}
