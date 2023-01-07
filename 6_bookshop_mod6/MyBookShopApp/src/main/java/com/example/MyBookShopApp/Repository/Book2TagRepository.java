package com.example.MyBookShopApp.Repository;

import com.example.MyBookShopApp.data.Book2TagEntity;
import com.example.MyBookShopApp.data.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Book2TagRepository extends JpaRepository<Book2TagEntity, Integer> {
}
