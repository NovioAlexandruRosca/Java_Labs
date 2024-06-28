package org.example.entityClasses;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@NamedQueries({@NamedQuery(
        name = "Book.findByName",
        query = "SELECT b FROM Book b WHERE b.title LIKE :title"
)})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "language", length = 50)
    private String language;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "num_pages")
    private Integer numPages;

    @JoinColumn(name = "publishing_house")
    @Column(name = "publishing_house")
    private Integer publishingHouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publishing_house_id")
    private PublishingHouse publishingHouse2;

    public PublishingHouse getPublishingHouse2() {
        return publishingHouse2;
    }

    public void setPublishingHouse2(PublishingHouse publishingHouse2) {
        this.publishingHouse2 = publishingHouse2;
    }

    public Integer getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(Integer publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

}