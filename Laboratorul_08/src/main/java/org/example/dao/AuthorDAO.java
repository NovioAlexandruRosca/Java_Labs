package org.example.dao;

import org.example.dataTypes.Author;
import org.example.interfaces.IAuthorDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO implements IAuthorDAO {

    /**
     * The connection to the database
     */
    private final Connection connection;

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * returns a specific author
     * @param authorId the authro id we want to return
     * @return an author after an id
     * @throws SQLException ...
     */
    public Author getAuthorById(int authorId) throws SQLException {
        String sql = "SELECT * FROM Authors WHERE author_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String authorName = resultSet.getString("author_name");
                    Integer bookId = resultSet.getInt("bookId");

                    Author author = new Author();
                    author.setName(authorName);
                    author.setBookId(bookId);

                    return author;
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * a method to return all the authors
     * @return returns a list of all authors
     * @throws SQLException ...
     */
    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM Authors";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String authorName = resultSet.getString("author_name");
                Integer bookId = resultSet.getInt("bookId");

                Author author = new Author();
                author.setName(authorName);
                author.setBookId(bookId);

                authors.add(author);
            }
        }
        return authors;
    }

    /**
     * Adds an author to the databse
     * @param author the author that needs to be added
     * @throws SQLException  exception thrown when an sql error occures
     */
    public void addAuthor(Author author) throws SQLException {
        String sql = "INSERT INTO Authors (author_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, author.getName());
            statement.executeUpdate();
        }
    }

    /**
     * updates the data of an author based on it s name and id
     * @param author the author
     * @param authorId it s id
     * @throws SQLException ...
     */
    public void updateAuthor(Author author, int authorId) throws SQLException {
        String sql = "UPDATE Authors SET author_name = ? , bookId = ? WHERE author_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, author.getName());
            statement.setInt(2, author.getBookId());
            statement.setInt(3, authorId);
            statement.executeUpdate();
        }
    }

    /**
     * delete an author
     * @param authorId the author id
     * @throws SQLException ...
     */
    public void deleteAuthor(int authorId) throws SQLException {
        String sql = "DELETE FROM Authors WHERE author_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, authorId);
            statement.executeUpdate();
        }
    }
}
