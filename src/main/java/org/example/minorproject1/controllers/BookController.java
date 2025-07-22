package org.example.minorproject1.controllers;

import jakarta.validation.Valid;
import org.example.minorproject1.dtos.CreateBookRequest;
import org.example.minorproject1.models.Book;
import org.example.minorproject1.models.Genre;
import org.example.minorproject1.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
//@Valid
public class BookController {

    private static Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @PostMapping("")
    public Book createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        log.info("Create Book: request received: {}", createBookRequest);
        return bookService.create(createBookRequest);
//        return bookService.create(createBookRequest.covertToBook());
    }

    @GetMapping("/find-by-name")
    public List<Book> findBookByName(@RequestParam("name") String name){
        return bookService.findByName(name);
    }

    @GetMapping("/find-by-genre")
    public List<Book> findBookByGenre(@RequestParam("genre") Genre genre){
        return bookService.findByGenre(genre);
    }

    @GetMapping("find-by-author")
    public List<Book> findBookByAuthor(@RequestParam("author") String author){
        return bookService.findByAuthorName(author);
    }

    @GetMapping("/find-all")
    public List<Book> findAll(){
        return bookService.findAll();
    }
}
