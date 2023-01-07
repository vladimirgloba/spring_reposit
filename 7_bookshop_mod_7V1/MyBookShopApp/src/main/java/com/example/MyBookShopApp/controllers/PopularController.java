package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.repository.Book2UserRepository;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.service.Book2UserService;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.service.BookService;

import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PopularController {

    private final BookService bookService;
private final Book2UserService book2UserService;
private final Book2UserRepository book2UserRepository;
    private final BookRepository bookRepository;
    @Autowired
    public PopularController(BookService bookService, Book2UserService book2UserService,
                             Book2UserRepository book2UserRepository,BookRepository bookRepository) {
        this.bookService = bookService;
        this.book2UserService=book2UserService;
        this.book2UserRepository=book2UserRepository;
        this.bookRepository=bookRepository;
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
        return bookService.getBooksData();
    }

    @ModelAttribute("category")
    public String category() {
        return "popular";
    }

    @ModelAttribute("popularBooks")
    public List<Book>popularBooks(){return responseForPopularBooks(20,0);}

    @GetMapping("/popular")
    public String popularPage() {

        return "/books/popular";
    }

//    @GetMapping("/books/popular")
//    @ResponseBody
//    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset,
//                                            @RequestParam("limit") Integer limit) {
    public List<Book> responseForPopularBooks(Integer limit, Integer offset){
        offset = offset * limit;
        List<Book> bookList = new ArrayList<>();
        if (limit + offset < book2UserRepository.book2UserCount()) {
            List<Object[]> objects = book2UserRepository.popularBooksId(limit, offset);
            for (Object[] object : objects) {
                Book book = bookRepository.findById(Integer.valueOf(object[0].toString())).get();
                bookList.add(book);

            }
        } else {
            offset = book2UserRepository.book2UserCount() - limit;
            List<Object[]> objects = book2UserRepository.popularBooksId(limit, offset);
            for (Object[] object : objects) {
                Book book = bookRepository.findById(Integer.valueOf(object[0].toString())).get();
                bookList.add(book);
            }
        }
       return bookList;
    }

}
