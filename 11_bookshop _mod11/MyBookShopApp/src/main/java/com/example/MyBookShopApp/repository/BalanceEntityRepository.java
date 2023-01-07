package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BalanceEntityRepository extends JpaRepository<BalanceEntity, Integer> {
    @Query(value = "select * from balance_transaction where user_id =?",nativeQuery = true)
    List<BalanceEntity> selectBalanceByUserId(Integer userId);

    @Query(value = "select sum(value) from balance_transaction where user_id =?",nativeQuery = true)
    Double selectManyByUserId(Integer userId);
}
