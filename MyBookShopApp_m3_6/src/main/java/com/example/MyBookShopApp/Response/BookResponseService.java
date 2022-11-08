package com.example.MyBookShopApp.Response;

import com.example.MyBookShopApp.data.AuthorService;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRowMapper;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BookResponseService {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    public List<BookResponse>bookResponses(){
        List<BookResponse>responses=new ArrayList<>();
     List<Book>books=bookService.findAllBooks();
        System.out.println("bookList size = "+books.size());
     for(Book book:books){
         BookResponse response=new BookResponse();
         response.setId(book.getId());

         response.setAuthor(authorService.findByAuthorId(book.getAuthorId()));
         response.setPrice(book.getPrice());
         response.setPriceOld(book.getPriceOld());
         response.setTitle(book.getTitle());
         responses.add(response);
     }
     return responses;
    }




}
