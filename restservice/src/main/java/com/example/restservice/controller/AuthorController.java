package com.example.restservice.controller;

import com.example.restservice.dao.AuthorDAO;
import com.example.restservice.entities.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorDAO authorDAO;

    @Autowired
    public AuthorController(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @GetMapping
    public List<Author> getAllAuthors() throws SQLException {
        return authorDAO.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) throws SQLException {
        Author author = authorDAO.getAuthorById(Math.toIntExact(id));
        if (author != null) {
            return ResponseEntity.ok(author);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) throws SQLException {
        authorDAO.deleteAuthor(Math.toIntExact(id));
        return ResponseEntity.ok("Author deleted successfully");
    }
}