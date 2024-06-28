package org.example;

import org.example.dao.AuthorDAO;
import org.example.dataTypes.Author;
import utility.Utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    /**
     * Logger for logging information
     */
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public static void main(String[] args) {

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();
            AuthorDAO authorDAO = new AuthorDAO(connection);
            List<Author> authorList = authorDAO.getAllAuthors();
            System.out.println(authorList);

        } catch (SQLException e) {
            logger.log(Level.INFO, Utility.textColoring("There have been an sql related error" + e.getMessage(), Utility.ansiEscapeCodes.RED));
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Failed to close connection: " + e.getMessage(), e);
                }
            }
        }
    }
}
