package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dataModele.Book;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("bookList")
    public List<Book> recommendedBooks(){
        List<Book> recommendations= new ArrayList<>();
        int i=0;
        while (i<=8){
            recommendations.add(bookService.getBooksData().get(i));
            i++;
        }
        return recommendations;
    }

    @ModelAttribute("bookList1")
    public List<Book> newBook(){
        List<Book> recommendations= new ArrayList<>();
        int i=9;
        while (i<18){
            recommendations.add(bookService.getBooksData().get(i));
            i++;
        }
        return recommendations;
    }



    @ModelAttribute("bookList2")
    public List<Book> popularBook(){
        List<Book> recommendations= new ArrayList<>();
        int i=18;
        while (i<25){
            recommendations.add(bookService.getBooksData().get(i));
            i++;
        }
        return recommendations;
    }


    @GetMapping("/")
    public String mainPage(){
        System.out.println(recommendedBooks().size());
        return "index";
    }

}
