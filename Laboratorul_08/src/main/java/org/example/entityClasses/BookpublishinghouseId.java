package org.example.entityClasses;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class BookpublishinghouseId implements java.io.Serializable {
    private static final long serialVersionUID = 3334804478014154954L;
    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(name = "publishing_house_id", nullable = false)
    private Integer publishingHouseId;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getPublishingHouseId() {
        return publishingHouseId;
    }

    public void setPublishingHouseId(Integer publishingHouseId) {
        this.publishingHouseId = publishingHouseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookpublishinghouseId entity = (BookpublishinghouseId) o;
        return Objects.equals(this.publishingHouseId, entity.publishingHouseId) &&
                Objects.equals(this.bookId, entity.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publishingHouseId, bookId);
    }

}