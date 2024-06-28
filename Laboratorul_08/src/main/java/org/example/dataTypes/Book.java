package org.example.dataTypes;

import java.time.LocalDate;

public class Book {

    private int id;
    private String title;
    private String language;
    private LocalDate publicationDate;
    private double numPages;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public double getNumPages() {
        return numPages;
    }

    public void setNumPages(double numPages) {
        this.numPages = numPages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", publicationDate=" + publicationDate +
                ", numPages=" + numPages +
                '}';
    }
}
