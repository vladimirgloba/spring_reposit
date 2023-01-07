package com.example.MyBookShopApp.Repository;

import com.example.MyBookShopApp.data.Book2GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Book2GenreRepository extends JpaRepository <Book2GenreEntity,Integer>{
}
