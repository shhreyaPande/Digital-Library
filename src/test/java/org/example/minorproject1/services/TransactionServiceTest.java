package org.example.minorproject1.services;

import jakarta.validation.ValidationException;
import org.example.minorproject1.models.*;
import org.example.minorproject1.repositories.TransactionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class) // which type of runner I need for writing test cases
public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    StudentService studentService;
    @Mock
    BookService bookService;

    @Test   // making this function act like a test case which is independently executable
    public void testIssueTxn(){

        String studentId = "112315075";
        Integer bookId = 2000;
        Integer transactionId = 5000;
        String externalTransactionId = UUID.randomUUID().toString();

        Student student = new Student();
        student.setRollNumber(studentId);

        Book book = new Book();
        book.setId(bookId);
        book.setIsAvailable(true);
        book.setStudent(null);
        book.setIssueCount(1L);

        Mockito.when(studentService.findByRollNumber(Mockito.eq(studentId))).thenReturn(student);
        Mockito.when(bookService.findById(Mockito.eq(bookId))).thenReturn(book);
        Mockito.when(bookService.findBooksByRollNumber(Mockito.eq(studentId))).thenReturn(new ArrayList<>());

        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setExternalTxnId(externalTransactionId);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setType(TransactionType.ISSUE);
        transaction.setStudent(student);
        transaction.setBook(book);

        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setIsAvailable(false);
        updatedBook.setStudent(student);
        updatedBook.setIssueCount(0L);

        Transaction updatedTransaction = new Transaction();      // t2 != t1
        updatedTransaction.setId(transactionId);
        updatedTransaction.setExternalTxnId(externalTransactionId);
        updatedTransaction.setStatus(TransactionStatus.SUCCESS);
        updatedTransaction.setType(TransactionType.ISSUE);
        updatedTransaction.setStudent(student);
        updatedTransaction.setBook(book);

        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transaction);
        Mockito.when(bookService.create((Book) Mockito.any())).thenReturn(updatedBook);
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(updatedTransaction);

        String actualExternalId = transactionService.issueTxn(studentId,bookId);

        Mockito.verify(bookService, Mockito.times(1)).findById(Mockito.eq(bookId));
        Mockito.verify(studentService, Mockito.times(1)).findByRollNumber(Mockito.eq(studentId));
        Mockito.verify(transactionRepository, Mockito.times(2)).save(Mockito.any());

        Assert.assertEquals(externalTransactionId, actualExternalId);

    }

    @Test(expected = ValidationException.class)
    public void testIssueTxn_StudentNotFound(){

        Mockito.when(studentService.findByRollNumber(Mockito.eq("112316048"))).thenReturn(null);
        transactionService.issueTxn("112316048",7);
    }

}
