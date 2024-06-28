package org.example.Repositories;


import org.example.PersistenceUnitConfig;
import org.example.StatementLogger;
import org.example.entityClasses.Author;
import org.example.entityClasses.Book;
import org.example.entityClasses.PublishingHouse;

import javax.persistence.EntityManager;

public class BookRepositoryTest {

    public static void main(String[] args) {
        StatementLogger.setupLogger();
        EntityManager em = PersistenceUnitConfig.getEntityManagerFactory().createEntityManager();
        BookRepository bookRepository = new BookRepository(em);
        PublishingHouseRepository publishingHouseRepository = new PublishingHouseRepository(em);
        AuthorRepository authorRepository = new AuthorRepository(em);

//        Book book = new Book();
//        book.setTitle("Book Title");
//        book.setId(231312);
//        bookRepository.create(book);

//        Book book1 = bookRepository.findById(2313123);

        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setId(1);
        publishingHouse.setTitle("CasaBella");

        publishingHouseRepository.create(publishingHouse);

//        Author author = new Author();
//        author.setId(32131231);
//        author.setAuthorName("George R. R. Martina");
//        authorRepository.create(author);


        em.close();
//        System.out.println(book1.getTitle());
    }
}
