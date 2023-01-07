package com.example.MyBookShopApp.data;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AuthorRowMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));
        author.setBirthday(rs.getDate("birthday"));

        return author;
    }
}
