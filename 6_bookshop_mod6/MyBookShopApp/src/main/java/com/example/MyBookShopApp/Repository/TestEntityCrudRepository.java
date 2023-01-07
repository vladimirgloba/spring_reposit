package com.example.MyBookShopApp.Repository;

import com.example.MyBookShopApp.data.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestEntityCrudRepository extends CrudRepository<TestEntity,Long> {
}
