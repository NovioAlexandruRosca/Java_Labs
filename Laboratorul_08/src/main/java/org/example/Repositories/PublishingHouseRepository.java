package org.example.Repositories;

import org.example.entityClasses.PublishingHouse;

import javax.persistence.EntityManager;

public class PublishingHouseRepository extends AbstractRepository<PublishingHouse> {
    public PublishingHouseRepository( EntityManager entityManager) {
        super(PublishingHouse.class, entityManager);
    }
}
