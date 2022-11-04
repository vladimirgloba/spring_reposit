package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dataModele.Book;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class PostponedController {

    private final BookService bookService;

    @Autowired
    public PostponedController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("bookList")
    public List<Book> bookList(){
        return bookService.getBooksData();
    }
   @GetMapping("/postponed")
public String postponedController(){
        return "/postponed";
}

}
