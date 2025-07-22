package org.example.minorproject1.services;
import org.example.minorproject1.models.Author;
import org.example.minorproject1.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author getOrCreate(Author author){
        // first check is the author exists or not
        Author existingAuthor = authorRepository.findByEmail(author.getEmail());
        if(existingAuthor == null){
            return authorRepository.save(author);
        }
        return existingAuthor;
    }
}
