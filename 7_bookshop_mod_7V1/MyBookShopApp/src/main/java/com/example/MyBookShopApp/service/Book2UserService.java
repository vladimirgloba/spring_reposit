package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.repository.AuthorRepository;
import com.example.MyBookShopApp.repository.Book2UserRepository;
import com.example.MyBookShopApp.repository.EntityManagerRepository;
import com.example.MyBookShopApp.data.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class Book2UserService {
    @Autowired
    private Book2UserRepository book2UserRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private EntityManagerRepository emRepo;

    @Autowired
    private BookService bookService;

    public Book2UserService() {
    }
}


