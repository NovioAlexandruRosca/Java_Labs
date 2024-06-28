package org.example.entityClasses;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class BookauthorId implements java.io.Serializable {
    private static final long serialVersionUID = -4739087345892007660L;
    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookauthorId entity = (BookauthorId) o;
        return Objects.equals(this.authorId, entity.authorId) &&
                Objects.equals(this.bookId, entity.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, bookId);
    }

}