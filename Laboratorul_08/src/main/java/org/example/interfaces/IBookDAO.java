package org.example.interfaces;

import org.example.dataTypes.Book;

import java.sql.SQLException;
import java.util.List;

public interface IBookDAO {
    Book getBookById(int bookId) throws SQLException;
    List<Book> getAllBooks() throws SQLException;
    void addBook(Book book) throws SQLException;
    void updateBook(Book book, int bookId) throws SQLException;
    void deleteBook(int bookId) throws SQLException;
}
