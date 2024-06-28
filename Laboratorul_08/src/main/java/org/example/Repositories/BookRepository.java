package org.example.Repositories;

import org.example.StatementLogger;
import org.example.entityClasses.Book;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BookRepository extends AbstractRepository<Book> {
    private final EntityManager entityManager;

    public BookRepository(EntityManager entityManager) {
        super(Book.class, entityManager);
        this.entityManager = entityManager;
    }

    public void create(Book book) {
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        StatementLogger.logInfo("created book: " + book);
    }

    public Book findById(Integer id) {
        StatementLogger.logInfo("findById: " + id);
        return entityManager.find(Book.class, id);
    }

    public List<Book> findByName(String name) {
        TypedQuery<Book> query = entityManager.createNamedQuery("Book.findByName", Book.class);
        query.setParameter("title", "%" + name + "%");
        return query.getResultList();
    }
}