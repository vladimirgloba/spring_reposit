package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.Response.BookResponse;
import com.example.MyBookShopApp.Response.BookResponseService;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class MainPageController {

@Autowired
    private  BookResponseService bookService;


    public MainPageController() {

    }

    @GetMapping("/")
    public String mainPage(Model model){
        model.addAttribute("bookResponseList", bookService.bookResponses());
        return "index";
    }
}
