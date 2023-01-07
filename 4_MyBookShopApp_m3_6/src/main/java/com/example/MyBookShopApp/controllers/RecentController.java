package com.example.MyBookShopApp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class RecentController {
//    private final BookService bookService;
//
//    @Autowired
//    public RecentController(BookService bookService) {
//        this.bookService = bookService;
//    }
//
//    @ModelAttribute("bookList")
//    public List<Book> bookList(){
//        return bookService.getBooksData();
//    }
//    @ModelAttribute("category")
//    public String category(){
//        return "recent";
//    }
    @GetMapping("/recent")
    public String recentPage(){

        return "/books/recent";
    }
}
