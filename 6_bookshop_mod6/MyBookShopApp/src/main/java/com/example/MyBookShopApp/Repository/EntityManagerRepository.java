package com.example.MyBookShopApp.Repository;

import com.example.MyBookShopApp.data.Book2UserEntity;
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

    @Transactional
    public void proba(){
Query query=em.createQuery("select  e.bookId, (count(e.typeId)+avg(e.typeId)*0.6) as c from Book2UserEntity e group by e.bookId order by c ");
        List<Object[]> result = query.getResultList();
        System.out.println(result.size());
        System.out.println(result.get(0)[0].toString()+" "+result.get(0)[1].toString());
    }
}
