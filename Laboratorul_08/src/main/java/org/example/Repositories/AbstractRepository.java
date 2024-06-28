package org.example.Repositories;

import org.example.StatementLogger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractRepository<T> {
    private final EntityManager entityManager;
    private final Class<T> entityClass;

    public AbstractRepository(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    public void create(T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            StatementLogger.logInfo("created " + entityClass.getSimpleName());
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            StatementLogger.logCritical(e);
            throw new PersistenceException("Error creating entity: " + entity, e);
        }

    }

    public T find(Object id) {
        T entity = null;

        try {
            entity = entityManager.find(this.entityClass, id);
        }catch (Exception e) {
            StatementLogger.logCritical(e);
        }

        StatementLogger.logInfo("found " + entityClass.getSimpleName());
        return entity;
    }

    public List<T> findAll() {
        TypedQuery<T> query = null;
        try {
            query = entityManager.createQuery("SELECT e FROM " + this.entityClass.getSimpleName() + " e", entityClass);
        }catch (Exception e) {
            StatementLogger.logCritical(e);
        }
        if(query != null) {
            StatementLogger.logInfo("found " + entityClass.getSimpleName());
            return query.getResultList();
        }
        return null;
    }
}
