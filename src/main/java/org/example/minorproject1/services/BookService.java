package org.example.minorproject1.services;

import org.example.minorproject1.dtos.CreateBookRequest;
import org.example.minorproject1.models.Author;
import org.example.minorproject1.models.Book;
import org.example.minorproject1.models.Genre;
import org.example.minorproject1.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    public Book create(CreateBookRequest createBookRequest){
        // DTO to model conversion in the service class
        Book book = createBookRequest.covertToBook();

        // at this point we only have email, name and country for author
        Author author = book.getAuthor();

//      author = this.authorService.create(author); // unique key constraint failed
        // as multiple books can have same author [as email is a unique key]

        author = this.authorService.getOrCreate(author);
        book.setAuthor(author);
        return this.bookRepository.save(book);

        /*
         * Option 1:
         * 1. You save a book author as null
         * 2. You save the author object i.e, create an author and get author id
         * 3. Attach the author id with the book object, i.e,
         *    update the book table set author_id = ? where b_id = ?
         *
         * Option 2:
         * 1. You save the author object i.e, create an author and get the author id
         * 2. You save the book i.e, create a book with author id so that a FK mapping
         *    can exist.
         */
    }

    public Book create(Book book){
        Author author = book.getAuthor();

        if(author != null && author.getId() == null){
            author = this.authorService.getOrCreate(author);
            book.setAuthor(author);
        }

        return this.bookRepository.save(book);
        // its does not call the BookRequest func., function is called in controller
    }

    public Book findById(Integer bookId){
        return this.bookRepository.findById(bookId).orElse(null);
    }

    public List<Book> findBooksByRollNumber(String rollNumber){
        return this.bookRepository.findByStudent_RollNumber(rollNumber);
    }


    public List<Book> findByName(String name){
        return this.bookRepository.findAllByTitle(name);
    }

    public List<Book> findByGenre(Genre genre) {
        return this.bookRepository.findByGenre(genre);
    }

    public List<Book> findByAuthorName(String author) {
        return this.bookRepository.findByAuthor_Name(author);
    }

    public List<Book> findAll(){
        return this.bookRepository.findAll();
    }

}
