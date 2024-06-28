package com.example.restservice.dao;

import com.example.restservice.entities.Book;
import com.example.restservice.interfaces.IBookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class BookDAO implements IBookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{bookId}, this::mapRowToBook);
    }

    @Override
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, this::mapRowToBook);
    }

    @Override
    public void addBook(Book book) {
        String sql = "INSERT INTO books (book_id, title, language, publication_date, num_pages) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getId(), book.getTitle(), book.getLanguage(), book.getPublicationDate(), book.getNumPages());
    }

    @Override
    public void updateBook(Book book, int bookId) {
        String sql = "UPDATE books SET title = ?, language = ?, publication_date = ?, num_pages = ? WHERE book_id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getLanguage(), book.getPublicationDate(), book.getNumPages(), bookId);
    }

    @Override
    public void deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id = ?";
        jdbcTemplate.update(sql, bookId);
    }

    public void updateBookName(int bookId, String newName) {
        String sql = "UPDATE books SET title = ? WHERE book_id = ?";
        jdbcTemplate.update(sql, new Object[]{newName, bookId}, new int[]{Types.VARCHAR, Types.INTEGER});
    }

    private Book mapRowToBook(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("book_id"));
        book.setTitle(resultSet.getString("title"));
        book.setLanguage(resultSet.getString("language"));

        java.sql.Date publicationDate = resultSet.getDate("publication_date");
        if (publicationDate != null) {
            book.setPublicationDate(publicationDate.toLocalDate());
        }

        book.setNumPages(resultSet.getInt("num_pages"));
        return book;
    }
}
