package com.globa.book_shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UpdateDataBase implements CommandLineRunner {
    @Autowired
    private BookService bookService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("+++++++++++++++in update bloc+++++++++++++++++++");
        bookService.createTableAuthor();
        jdbcTemplate.update("DROP TABLE IF EXISTS books;");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
