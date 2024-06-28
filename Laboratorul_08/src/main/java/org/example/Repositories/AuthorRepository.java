package org.example.Repositories;

import org.example.entityClasses.Author;

import javax.persistence.EntityManager;

public class AuthorRepository extends AbstractRepository<Author> {
    public AuthorRepository( EntityManager entityManager) {
        super(Author.class, entityManager);
    }
}
