package org.example.minorproject1.repositories;

import org.example.minorproject1.models.Book;
import org.example.minorproject1.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByStudent_RollNumber(String studentRollNumber);

    List<Book> findAllByTitle(String title);

    List<Book> findByGenre(Genre genre);

    List<Book> findByAuthor_Name(String authorName);
}
