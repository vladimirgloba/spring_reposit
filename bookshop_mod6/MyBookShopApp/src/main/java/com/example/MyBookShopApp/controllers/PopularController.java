package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class PopularController {

    private final BookService bookService;

    @Autowired
    public PopularController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("bookList")
    public List<Book> bookList() {
        return bookService.getBooksData();
    }

    @ModelAttribute("category")
    public String category() {
        return "popular";
    }

    @GetMapping("/books/popular")
    public String popularPage() {
        System.out.println(bookList().size() + "++++++++++++++++++++++++++++++++++++++++++++++");
        return "/books/popular";
    }
}
