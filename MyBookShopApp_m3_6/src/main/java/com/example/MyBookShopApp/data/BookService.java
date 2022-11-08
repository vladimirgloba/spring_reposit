package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {



    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Book findByBookId(Integer id) {

        String sql = "SELECT * FROM BOOKS WHERE ID = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BookRowMapper());

    }
    public List<Book> findAllBooks() {

        String sql = "SELECT * FROM BookS";

        List<Book> books = jdbcTemplate.query(
                sql,
                new BookRowMapper());

        return books;

    }
}
