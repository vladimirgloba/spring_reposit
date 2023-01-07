package com.example.MyBookShopApp.data;

import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setAuthorId(rs.getInt("author_id"));
        book.setTitle(rs.getString("title"));
        book.setPriceOld(rs.getInt("price_Old"));
        book.setPrice(rs.getInt("price"));
        return book;
    }
}
