package com.example.MyBookShopApp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class EntityManagerRepository {
    @PersistenceContext
    EntityManager em;
    @Transactional
    public void updateBD(String sql){
        em.createNativeQuery(sql).executeUpdate();
    }
}
