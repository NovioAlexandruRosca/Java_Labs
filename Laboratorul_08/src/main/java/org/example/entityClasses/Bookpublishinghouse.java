package org.example.entityClasses;

import javax.persistence.*;

@Entity
@Table(name = "bookpublishinghouse")
public class Bookpublishinghouse {
    @EmbeddedId
    private BookpublishinghouseId id;

    @MapsId("bookId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @MapsId("publishingHouseId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publishing_house_id", nullable = false)
    private PublishingHouse publishingHouse;

    public BookpublishinghouseId getId() {
        return id;
    }

    public void setId(BookpublishinghouseId id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public PublishingHouse getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(PublishingHouse publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

}