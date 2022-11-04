package com.example.MyBookShopApp.dataService;

import com.example.MyBookShopApp.dataModele.Book;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepo;
    public List<Book>getBooksData(){
        return bookRepo.findAll();
    }
    public void addAllBooks(List<Book>books){
        bookRepo.saveAll(books);
    }

//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public BookService(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Book> getBooksData() {
//        List<Book> books = jdbcTemplate.query("SELECT * FROM books",(ResultSet rs, int rowNum) -> {
//            Book book = new Book();
//            book.setId(rs.getInt("id"));
//            book.setAuthor(rs.getString("author"));
//            book.setTitle(rs.getString("title"));
//            book.setPriceOld(rs.getString("priceOld"));
//            book.setPrice(rs.getString("price"));
//            return book;
//        });
//        return new ArrayList<>(books);
//    }
}
