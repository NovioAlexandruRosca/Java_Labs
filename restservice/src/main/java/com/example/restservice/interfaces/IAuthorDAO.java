package com.example.restservice.interfaces;

import com.example.restservice.entities.Author;

import java.sql.SQLException;
import java.util.List;

public interface IAuthorDAO {
    Author getAuthorById(int authorId) throws SQLException;
    List<Author> getAllAuthors() throws SQLException;
    void addAuthor(Author author) throws SQLException;
    void updateAuthor(Author author, int authorId) throws SQLException;
    void deleteAuthor(int authorId) throws SQLException;
}
