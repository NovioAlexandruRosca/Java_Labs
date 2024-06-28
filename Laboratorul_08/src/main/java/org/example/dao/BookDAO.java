package org.example.dao;

import org.example.dataTypes.Book;
import org.example.interfaces.IBookDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements IBookDAO {
    private final Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractBookFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(extractBookFromResultSet(resultSet));
            }
        }
        return books;
    }

    @Override
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (book_id, title, language, publication_date, num_pages) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getLanguage());
            statement.setDate(4, java.sql.Date.valueOf(book.getPublicationDate()));
            statement.setDouble(5, book.getNumPages());
            statement.executeUpdate();
        }
    }

    @Override
    public void updateBook(Book book, int bookId) throws SQLException {
        String sql = "UPDATE books SET title = ?, language = ?, publication_date = ?, num_pages = ? WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getLanguage());
            statement.setDate(3, java.sql.Date.valueOf(book.getPublicationDate()));
            statement.setDouble(4, book.getNumPages());
            statement.setInt(5, bookId);
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteBook(int bookId) throws SQLException {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            statement.executeUpdate();
        }
    }

    private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("book_id"));
        book.setTitle(resultSet.getString("title"));
        book.setLanguage(resultSet.getString("language"));
        book.setPublicationDate(resultSet.getDate("publication_date").toLocalDate());
        book.setNumPages(resultSet.getInt("num_pages"));
        return book;
    }
}
