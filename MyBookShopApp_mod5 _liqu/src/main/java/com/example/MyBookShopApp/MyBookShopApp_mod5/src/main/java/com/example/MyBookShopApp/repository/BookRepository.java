package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.dataModele.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository <Book, Long>{
}
