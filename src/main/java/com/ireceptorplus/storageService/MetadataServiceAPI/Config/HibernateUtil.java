package com.ireceptorplus.storageService.MetadataServiceAPI.Config;

import com.fasterxml.classmate.AnnotationConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.imageio.spi.ServiceRegistry;
import javax.persistence.EntityManagerFactory;

@Configuration
public class HibernateUtil {

    @Autowired
    private EntityManagerFactory factory;

    @Bean
    public SessionFactory getSessionFactory() {
        if(factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("Factory is not a hibernate factory.");
        }
        return factory.unwrap(SessionFactory.class);
    }
}