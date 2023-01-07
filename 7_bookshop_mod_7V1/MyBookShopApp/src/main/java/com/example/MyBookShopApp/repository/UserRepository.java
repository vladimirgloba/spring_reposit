package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.TestEntity;
import com.example.MyBookShopApp.data.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {
}
