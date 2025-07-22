package org.example.minorproject1.services;

import jakarta.validation.ValidationException;
import org.example.minorproject1.models.*;
import org.example.minorproject1.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

   @Value("${books.issue.max-allowed}")
    Integer maxAllowedBooks;

   @Value("${books.issue.duration}")
   Integer maxAllowedBookDuration;

   @Value("${books.fine.per-day}")
   Integer finePerDay;

   @Autowired
   private BookService bookService;

   @Autowired
   private StudentService studentService;

   @Autowired
   private TransactionRepository transactionRepository;

    public TransactionService() {
        this.maxAllowedBooks = 3;
        this.maxAllowedBookDuration = 15;
        this.finePerDay = 2;
    }

    public String issueTxn(String studentId, Integer bookId) {

    /*
     * 1. book id should be valid and is Available
     * 2. student id should be valid and number of books issued should be less than maxAllowed
     *
     *
    */

        Book book = bookService.findById(bookId);
//        if(book != null && book.getIsAvailable()){
//            // bottom one and this one both are implementing same logic.
//        }

        if(book == null || book.getStudent() != null){
            throw new ValidationException("Book is not available for issue transaction");
        }
        Student student = studentService.findByRollNumber(studentId);
        if(student == null){
            throw new ValidationException("Student Roll Number is invalid");
        }

        List<Book> bookList = bookService.findBooksByRollNumber(studentId);
        if(bookList == null ||  bookList.size() >= maxAllowedBooks){
            throw new ValidationException("Book limit exceeded");
        }

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.ISSUE);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setExternalTxnId(UUID.randomUUID().toString());
        transaction.setBook(book);
        transaction.setStudent(student);

        // saved as transaction with pending status
        transaction = this.transactionRepository.save(transaction);
    // getting the transaction object because it has the id coming from the underlying db

        try{
            book.setIsAvailable(false);
            book.setStudent(student);
            book.setIssueCount(book.getIssueCount() + 1);
            this.bookService.create(book);
            transaction.setStatus(TransactionStatus.SUCCESS);

        }catch(Exception e){
            book.setIsAvailable(true);
            book.setStudent(null);
            book.setIssueCount(book.getIssueCount() -1);
            this.bookService.create(book); // Roll Back
            transaction.setStatus(TransactionStatus.FAILED);

        }finally{
            this.transactionRepository.save(transaction);
        }

        return transaction.getExternalTxnId();
    }

    public String returnTxn(String studentId, Integer bookId){

        // book.studentId != null --> It only checks whether the book is assigned to some student
        // book.studentId == studentId --> It checks whether the book is assigned and that to
        //                                 the student with id studentId.

        Book book = bookService.findById(bookId);
        if(book == null || book.getStudent() == null || !book.getStudent().getRollNumber().equals(studentId)){
            throw new ValidationException("Book is not assigned to relevant student");
        }

        Student student = studentService.findByRollNumber(studentId);

        Transaction transaction = new Transaction();
        transaction.setExternalTxnId(UUID.randomUUID().toString());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setType(TransactionType.RETURN);
        transaction.setBook(book);
        transaction.setStudent(student);
        transaction.setFineAmount(calculateFine(book, student));

        transaction = transactionRepository.save(transaction);

        try{
            book.setIsAvailable(true);
            book.setStudent(null);
            bookService.create(book);
            // update book with availability as true and student as null since book object
            // an id so it will be merged not persisted

            transaction.setStatus(TransactionStatus.SUCCESS);
        }catch(Exception e){
            book.setIsAvailable(false);
            book.setStudent(student);
            bookService.create(book);
            transaction.setStatus(TransactionStatus.FAILED);
        }finally{
            this.transactionRepository.save(transaction);
        }

        return transaction.getExternalTxnId();

    }

    public Long calculateFine(Book book, Student student){
        // ((ReturnDate - DueDate)*1 < 0) ? 0 : (ReturnDate - DueDate)*1 ;
        Transaction t = transactionRepository.
                findTopByBookAndStudentAndTypeAndStatusOrderByIdDesc
                        (book, student,TransactionType.ISSUE, TransactionStatus.SUCCESS);

        long daysPassed = ChronoUnit.DAYS
                .between(t.getCreatedAt().toInstant(), new Date().toInstant());

        if((daysPassed - maxAllowedBookDuration) <= 0){
            return 0L;
        }
        return (daysPassed-maxAllowedBookDuration)*finePerDay;

//        Date issueDate = t.getCreatedAt();
//        // epoch is the number of milliseconds passed since 1st jan 1970 till the issue date
//        long issueDateEpoch = issueDate.getTime();
//
//        long dueDateEpoch = issueDateEpoch + (maxAllowedBookDuration * 1296000000);
//
//        long currentDateEpoch = System.currentTimeMillis();
//
//        long timeDiffEpoch = currentDateEpoch - dueDateEpoch;
//
//        if(timeDiffEpoch > 0){
//            Long daysPastDueDate = TimeUnit.DAYS.convert(timeDiffEpoch, TimeUnit.MILLISECONDS);
//            return (daysPastDueDate*finePerDay);
//        }
//        return 0L;
    }
}
