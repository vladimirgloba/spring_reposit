package com.example.MyBookShopApp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EntityManagerRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void updateDB(String sql){
        em.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public List<Object[]> nativeQuerySelect(String sql){
      return   em.createNativeQuery(sql).getResultList();
    }

   }
