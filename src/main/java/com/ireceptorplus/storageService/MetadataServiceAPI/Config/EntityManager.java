package com.ireceptorplus.storageService.MetadataServiceAPI.Config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManager
{

    private static EntityManagerFactory factory;

    public static javax.persistence.EntityManager getEntityManager() {
        if (factory == null) {
            EntityManagerFactory factory =
                    Persistence.createEntityManagerFactory("jpa-tutorial");
        }
        javax.persistence.EntityManager entityManager = factory.createEntityManager();
        return entityManager;
    }
}