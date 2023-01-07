package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book2TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Book2TagRepository extends JpaRepository<Book2TagEntity, Integer> {
}
