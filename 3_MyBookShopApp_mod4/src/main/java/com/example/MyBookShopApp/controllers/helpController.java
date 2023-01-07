package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class helpController {

    @GetMapping("/faq")
    public String helpPage(){
        return "/faq";
    }
}
