package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dataModele.Author;
import com.example.MyBookShopApp.dataService.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@Controller
public class AuthorsController {
@Autowired
    private AuthorService authorService;

    @Autowired
    public AuthorsController() {

    }

    @ModelAttribute("authorsMap")
    public Map<String,List<Author>> authorsMap(){
        return authorService.getAuthorsMap();
    }


    @GetMapping("/authors")
    public String authorsPage(){
        return "/authors/index";
    }
}
