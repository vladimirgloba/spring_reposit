package com.example.MyBookShopApp.Repository;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorRepository  extends JpaRepository<Author, Integer> {


}
