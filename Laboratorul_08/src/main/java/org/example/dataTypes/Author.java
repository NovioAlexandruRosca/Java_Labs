package org.example.dataTypes;

public class Author {
    private String name;
    private Integer bookId;

    public Author() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Author{" + "bookId='" + bookId + '\'' +  "name='" + name + '\'' + '}';
    }
}
