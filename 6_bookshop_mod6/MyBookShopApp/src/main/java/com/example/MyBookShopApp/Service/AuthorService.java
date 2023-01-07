package com.example.MyBookShopApp.Service;

import com.example.MyBookShopApp.Repository.AuthorRepository;
import com.example.MyBookShopApp.Repository.EntityManagerRepository;
import com.example.MyBookShopApp.data.Author;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    AuthorRepository authorRepository;
@Autowired
    EntityManagerRepository em;
    public AuthorService() {

    }

    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors = authorRepository.findAll();


        return authors.stream().collect(Collectors.groupingBy((Author a) -> {return a.getLastName().substring(0,1);}));
    }


}
