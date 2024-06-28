package com.example.restservice.dao;

import com.example.restservice.entities.Author;
import com.example.restservice.interfaces.IAuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AuthorDAO implements IAuthorDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Author getAuthorById(int authorId) {
        String sql = "SELECT * FROM Authors WHERE author_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{authorId}, new AuthorRowMapper());
    }

    public List<Author> getAllAuthors() {
        String sql = "SELECT * FROM Authors";
        return jdbcTemplate.query(sql, new AuthorRowMapper());
    }

    public void addAuthor(Author author) {
        String sql = "INSERT INTO Authors (author_name) VALUES (?)";
        jdbcTemplate.update(sql, author.getName());
    }

    public void updateAuthor(Author author, int authorId) {
        String sql = "UPDATE Authors SET author_name = ?, bookId = ? WHERE author_id = ?";
        jdbcTemplate.update(sql, author.getName(), author.getBookId(), authorId);
    }

    public void deleteAuthor(int authorId) {
        String sql = "DELETE FROM Authors WHERE author_id = ?";
        jdbcTemplate.update(sql, authorId);
    }

    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setName(rs.getString("author_name"));
            author.setBookId(rs.getInt("bookId"));
            return author;
        }
    }
}
