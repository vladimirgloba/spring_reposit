package com.globa.book_shop.controller;

import com.globa.book_shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookshop")
public class AuthorsController {
    @Autowired
    BookService bookService;
    @GetMapping("/authors")
    public String mainPage(Model model) {
       model.addAttribute("authorList", bookService.getAuthorList());
        return "authors";
    }
}
