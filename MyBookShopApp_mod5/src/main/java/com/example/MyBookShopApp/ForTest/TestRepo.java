package com.example.MyBookShopApp.ForTest;

import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TestRepo {
    @PersistenceContext
    EntityManager em;
    @Transactional(readOnly = false)
    public void createOrUpdateOrDelete(String sql){
        em.createNativeQuery(sql).executeUpdate();
    }
    @Transactional
    public  java.util.List read(String sql){
        return em.createNativeQuery(sql).getResultList();
    }
}
