package com.globa.book_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class GenresController {

   @GetMapping("/genres")
   public String mainPage(Model model) {
//       model.addAttribute("bookData", bookService.getBooksData());
       return "genres";
   }
}
