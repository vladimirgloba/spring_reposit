package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BalanceEntity;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.CartDto;
import com.example.MyBookShopApp.data.CartDtoService;
import com.example.MyBookShopApp.repository.BalanceEntityRepository;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
public class BookshpCartController {
    private final BookRepository bookRepository;
    @Autowired
    CartDtoService cartDtoService;
    @Autowired
    BalanceEntityRepository balanceEntityRepository;
    boolean NoMany = false;

    @Autowired
    public BookshpCartController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
            int sumOld = 0;
            int sum = 0;
            for (Book book : booksFromCookieSlugs) {
                sum = sum + book.discountPrice();
                sumOld = sumOld + book.getPriceOld();
            }
            model.addAttribute("sumOld", sumOld);
            model.addAttribute("sum", sum);
            model.addAttribute("bookCart", booksFromCookieSlugs);
            model.addAttribute("NoMany", NoMany);
        }
        NoMany = false;
        return "cart";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "cartContents", required = false) String cartContents, HttpServletResponse response, Model model) {

        if (cartContents != null && !cartContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            model.addAttribute("isCartEmpty", true);
        }

        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug, @CookieValue(name = "cartContents",
            required = false) String cartContents, HttpServletResponse response, Model model) {

        if (cartContents == null || cartContents.equals("")) {
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }

        return "redirect:/books/" + slug;
    }

    @PostMapping("/purchase")
    public String purchase(HttpServletRequest request, @CookieValue(value = "cartContents", required = false) String cartContents,
                           Model model, HttpServletResponse response) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartEmpty", true);
        } else {
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
            int bookSum = 0;
            for (Book book : booksFromCookieSlugs) {
                bookSum = bookSum + book.discountPrice();
            }
            CartDto cartDto = new CartDto();
            cartDto = cartDtoService.getCartDto(request);
            String[] str = cartDto.getMany().split(" ");
            int sum = Integer.parseInt(str[2]);
            if (sum >= bookSum) {
                for (Book book : booksFromCookieSlugs) {
                    BalanceEntity balanceEntity = new BalanceEntity();
                    Integer id = Integer.parseInt(String.valueOf(balanceEntityRepository.count()));
                    id = id == null ? 0 : id;
                    balanceEntity.setId(id + 1);
                    balanceEntity.setValue(book.discountPrice() * -1);
                    balanceEntity.setTime(LocalDateTime.now());
                    balanceEntity.setUserId(cartDto.getUser().getId());
                    balanceEntity.setDescription("Списание, покупка книги \"" + book.getTitle() + "\"");
                    balanceEntity.setBookId(book.getId());
                    balanceEntityRepository.save(balanceEntity);
                }
                        Cookie cookie = new Cookie("cartContents", "");
                        cookie.setPath("/books");
                        response.addCookie(cookie);


            } else {
                NoMany = true;
            }
        }
        return "redirect:/books/cart";
    }
}
