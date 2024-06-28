package org.example;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUnitConfig {
    private static final String PERSISTENCE_UNIT_NAME = "BookCollectionPU";
    private static EntityManagerFactory entityManagerFactory;

    public PersistenceUnitConfig() {}

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return entityManagerFactory;
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}