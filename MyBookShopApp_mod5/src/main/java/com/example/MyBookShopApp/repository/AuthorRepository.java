package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.dataModele.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository <Author,Long>{
}
