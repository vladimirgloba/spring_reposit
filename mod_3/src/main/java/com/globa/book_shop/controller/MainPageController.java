package com.globa.book_shop.controller;

import com.globa.book_shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        System.out.println( bookService.getBookList().size());
        model.addAttribute("bookList", bookService.getBookList());
        return "index";
    }
}