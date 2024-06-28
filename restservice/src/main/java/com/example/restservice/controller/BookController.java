package com.example.restservice.controller;

import com.example.restservice.dao.BookDAO;
import com.example.restservice.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() throws SQLException {
        List<Book> books = bookDAO.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) throws SQLException {
        Book book = bookDAO.getBookById(Math.toIntExact(id));
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) throws SQLException {
        bookDAO.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully");
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<String> updateBookName(@PathVariable("id") int bookId, @RequestBody Map<String, String> requestBody) {
        String newName = requestBody.get("name");
        if (newName == null || newName.isEmpty()) {
            return ResponseEntity.badRequest().body("New name cannot be empty");
        }
        try {
            bookDAO.updateBookName(bookId, newName);
            return ResponseEntity.ok("Book name updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update book name");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) throws SQLException {
        bookDAO.deleteBook(Math.toIntExact(id));
        return ResponseEntity.ok("Book deleted successfully");
    }
}
