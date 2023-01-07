package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RecentController {
    private final BookService bookService;

    @Autowired
    public RecentController(BookService bookService) {
        this.bookService = bookService;
    }
    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("bookList")
    public List<Book> bookList() {
        Date before=Date.valueOf(LocalDate.now().minusMonths(1).toString());
        Date after=Date.valueOf(LocalDate.now().toString());
        return bookService.sortedListBooksByDate(before,after,20,0);
    }

    @ModelAttribute("category")
    public String category() {
        return "recent";
    }

    @GetMapping("/recent")
    public String recentPage() {

        return "/books/recent";
    }



}
