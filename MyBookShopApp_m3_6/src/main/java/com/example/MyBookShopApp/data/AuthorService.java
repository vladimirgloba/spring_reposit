package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Author findByAuthorId(Integer id) {

        String sql = "SELECT * FROM AUTHORS WHERE ID = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new AuthorRowMapper());

    }
    public List<Author> findAllAuthors() {

        String sql = "SELECT * FROM authors";

        List<Author> authors = jdbcTemplate.query(
                sql,
                new AuthorRowMapper());

        return authors;

    }
    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors=findAllAuthors();
        return authors.stream().collect(Collectors.groupingBy((Author a) -> {return a.getLastName().substring(0,1);}));
    }
}
