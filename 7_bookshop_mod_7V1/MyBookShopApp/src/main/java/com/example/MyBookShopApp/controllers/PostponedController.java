package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class PostponedController {

    private final CookieService cookieService;
    private final BookRepository bookRepository;

    @Autowired
    public PostponedController(CookieService cookieService, BookRepository bookRepository) {

        this.cookieService = cookieService;
        this.bookRepository = bookRepository;
    }

    @ModelAttribute(name = "bookList")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @GetMapping("/postponed")
    public String postponedController(HttpServletRequest request, HttpServletResponse response,
                                      Model model) {
        model.addAttribute("countInPostponed",cookieService.countBookInCookie(response,request,"KEPT")==null?0:cookieService.countBookInCookie(response,request,"KEPT"));
        model.addAttribute("countInCart",cookieService.countBookInCookie(response,request,"CART")==null?0:cookieService.countBookInCookie(response,request,"CART"));
        if (cookieService.countBookInCookie(response, request, "KEPT") == 0) {
            model.addAttribute("isCartEmpty", true);
            model.addAttribute("sumPrice", "0");
            model.addAttribute("sumOldPrice", "0");
            System.out.println("cookieService.countBookInCookie(response, request, KEPT) = "+cookieService.countBookInCookie(response, request, "KEPT"));
        } else {
            model.addAttribute("isCartEmpty", false);
            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieService.cookieSlugsForListBook
                    (request, "KEPT"));
            model.addAttribute("bookList", booksFromCookieSlugs);
            model.addAttribute("sumPrice", sumPrice(booksFromCookieSlugs));
            model.addAttribute("sumOldPrice", sumOldPrice(booksFromCookieSlugs));
        }

        return "/postponed";
    }
    private Integer sumPrice(List<Book> bookList) {
        Integer buffer = 0;
        if (!bookList.isEmpty()) {
            for (Book book : bookList) {
                buffer = buffer + book.discountPrice();
            }
            return buffer;
        }
        return 0;
    }

    private Integer sumOldPrice(List<Book> bookList) {
        Integer buffer = 0;
        if (!bookList.isEmpty()) {
            for (Book book : bookList) {
                buffer = buffer + book.getPriceOld();
            }
            return buffer;
        }
        return 0;
    }
}
