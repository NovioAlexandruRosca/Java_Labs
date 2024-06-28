package org.example;

import org.example.dao.AuthorDAO;
import org.example.dao.BookDAO;
import org.example.dataTypes.Author;
import org.example.dataTypes.Book;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataImporter {
    public static void importBooks(String filePath, Connection connection) throws IOException, SQLException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            if ((line = br.readLine()) != null) {
                String[] headers = line.split(",");

                int idIndex = -1;
                int titleIndex = -1;
                int authorIndex = -1;
                int languageIndex = -1;
                int numPagesIndex = -1;
                int publicationDateIndex = -1;

                for (int i = 0; i < headers.length; i++) {
                    switch (headers[i]) {
                        case "bookID":
                            idIndex = i;
                            break;
                        case "title":
                            titleIndex = i;
                            break;
                        case "authors":
                            authorIndex = i;
                            break;
                        case "language_code":
                            languageIndex = i;
                            break;
                        case "num_pages":
                            numPagesIndex = i;
                            break;
                        case "publication_date":
                            publicationDateIndex = i;
                            break;
                    }
                }

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");

                    int id = Integer.parseInt(data[idIndex]);
                    String title = data[titleIndex];
                    String author = data[authorIndex];
                    String language = data[languageIndex];
                    int numPages = Integer.parseInt(data[numPagesIndex]);
                    LocalDate publicationDate = LocalDate.parse(data[publicationDateIndex], DateTimeFormatter.ofPattern("M/d/yyyy"));

                    if (author.length() > 250) {
                        author = author.substring(0, 250);
                    }

                    BookDAO bookDAO = new BookDAO(connection);
                    AuthorDAO authorDAO = new AuthorDAO(connection);

                    Book book = new Book();
                    Author author1 = new Author();

                    book.setId(id);
                    book.setTitle(title);
                    book.setLanguage(language);
                    book.setNumPages(numPages);
                    book.setPublicationDate(publicationDate);

                    author1.setName(author);
                    author1.setBookId(id);

                    bookDAO.addBook(book);
                    authorDAO.addAuthor(author1);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}