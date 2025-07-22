package org.example.minorproject1.repositories;

import org.example.minorproject1.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

 // select t from transaction t where t.book.id = ?1 and t.student.id = ?2 and
 // t.transactionType = ISSUE and t.transactionStatus = SUCCESS order by id desc limit 1

    /*
    * S1  --> issued  -->    B1  T1  SUCCESS
    * S1  --> returned  -->  B1  T2  SUCCESS
    * S1  --> issued  -->    B1  T3  FAILED
    * S1  --> issued  -->    B1  T4
    *
    * -------
    * S1  --> returned  -->  B1  T5
    */

    Transaction findTopByBookAndStudentAndTypeAndStatusOrderByIdDesc
            (Book book, Student student, TransactionType transactionType,
             TransactionStatus transactionStatus);
}
