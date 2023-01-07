package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.AuthorService;
import com.sun.jdi.VMOutOfMemoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorsController {
@Autowired
    private AuthorService authorService;


    public AuthorsController() {

    }




    @GetMapping("/authors")
    public String authorsPage(Model model) {
        model.addAttribute("authorsMap",authorService.getAuthorsMap());
        return "/authors/index";
    }
}
