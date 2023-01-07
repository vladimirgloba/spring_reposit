package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.repository.AuthorRepository;
import com.example.MyBookShopApp.repository.EntityManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    EntityManagerRepository em;

    public AuthorService() {

    }

    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors = authorRepository.findAll();


        return authors.stream().collect(Collectors.groupingBy((Author a) -> {
            return a.getLastName().substring(0, 1);
        }));
    }


}
