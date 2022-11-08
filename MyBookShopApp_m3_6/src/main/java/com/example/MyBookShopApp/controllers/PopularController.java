package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.Response.BookResponse;
import com.example.MyBookShopApp.Response.BookResponseService;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class PopularController {
//@Autowired
//    private BookResponseService bookService;




    public PopularController() {

    }

//    @ModelAttribute("bookList")
//    public List<BookResponse> bookList() {
//        return bookService.getBooksData();
//    }
//
//    @ModelAttribute("category")
//    public String category() {
//        return "popular";
//    }

    @GetMapping("/popular")
    public String popularPage() {
//        System.out.println(bookList().size() + "++++++++++++++++++++++++++++++++++++++++++++++");
        return "/books/popular";
    }
}
