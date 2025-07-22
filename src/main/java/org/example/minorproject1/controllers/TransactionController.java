package org.example.minorproject1.controllers;

import org.example.minorproject1.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/issue")
    public String issueTxn(@RequestParam("student-rollNo") String studentId,
                           @RequestParam("book-id") Integer bookId) {

        return transactionService.issueTxn(studentId, bookId);
    }

    @PostMapping("/return")
    public String returnTxn(@RequestParam("student-rollNo") String studentId,
                            @RequestParam("book-id") Integer bookId){
        return transactionService.returnTxn(studentId, bookId);
    }
}
