package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.GenreEntity;
import com.example.MyBookShopApp.data.StarsDto;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.service.CookieService;
import com.example.MyBookShopApp.service.GenreService;
import com.example.MyBookShopApp.service.LikeAndReviewService;
import com.example.MyBookShopApp.service.StarsDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksShopCartController {
    private final CookieService cookieService;
    private final BookRepository bookRepository;
    private final StarsDtoService starsDtoService;
    private final GenreService genreService;
    private final LikeAndReviewService likeAndReviewService;

    @Autowired
    public BooksShopCartController(CookieService cookieService, BookRepository bookRepository, StarsDtoService starsDtoService, GenreService genreService, LikeAndReviewService likeAndReviewService) {
        this.cookieService = cookieService;
        this.bookRepository = bookRepository;
        this.starsDtoService = starsDtoService;
        this.genreService = genreService;
        this.likeAndReviewService = likeAndReviewService;
    }

    @ModelAttribute(name = "bookList")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @GetMapping("/cart")
    public String handleCartRequest(HttpServletRequest request, HttpServletResponse response,
                                    Model model) {
        model.addAttribute("countInPostponed", cookieService.countBookInCookie(response, request, "KEPT") == 0 ? 0 : cookieService.countBookInCookie(response, request, "KEPT"));
        model.addAttribute("countInCart", cookieService.countBookInCookie(response, request, "CART") == 0 ? 0 : cookieService.countBookInCookie(response, request, "CART"));
        if (cookieService.countBookInCookie(response, request, "CART") == 0) {
            model.addAttribute("isCartEmpty", true);
            model.addAttribute("sumPrice", "0");
            model.addAttribute("sumOldPrice", "0");

        } else {
            model.addAttribute("isCartEmpty", false);
            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieService.cookieSlugsForListBook
                    (request, "CART"));
            model.addAttribute("bookList", booksFromCookieSlugs);
            model.addAttribute("sumPrice", sumPrice(booksFromCookieSlugs));
            model.addAttribute("sumOldPrice", sumOldPrice(booksFromCookieSlugs));
        }
        return "cart";
    }

    @PostMapping("/changeBookStatus/cart/remove/")
    public String handleRemoveBookFromCartRequest(@RequestParam("slug") String slug,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {
        String status = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getValue().contains(slug)) {
                status = c.getName();
                ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(c.getValue().split("/")));
                cookieBooks.remove(slug);
                Cookie cookie = new Cookie(c.getName(), String.join("/", cookieBooks));
                cookie.setPath("/books");
                response.addCookie(cookie);
            }
        }
        return status.contains("CART") ? "redirect:/books/cart" : "redirect:/books/postponed";
    }

    @PostMapping("/rateBook")
    @ResponseBody
    public Object changeRatingStars(@RequestParam("bookSlug") String slug,
                                    @RequestParam("value") int value) {
        StarsDto starsDto = new StarsDto();
        starsDto.setResult(starsDtoService.insertOrSetRating(slug, value, 50));
        return starsDto;
    }

    @PostMapping("/rateBookReview")
    @ResponseBody
    public Object changeLike(@RequestParam("reviewid") Integer reviewId,
                             @RequestParam("value") int value) {
        StarsDto starsDto = new StarsDto();
        starsDto.setResult(likeAndReviewService.setOrInsertLike(reviewId, 50, value));
        return starsDto;
    }

    //
    @PostMapping("/bookReview")
    @ResponseBody
    public Object saveOrUpdateComment(@RequestParam("bookId") String slug,
                                      @RequestParam("text") String text) {
        StarsDto starsDto = new StarsDto();
        starsDto.setResult(likeAndReviewService.insertOrUpdateComments(slug, 50, text));
        return starsDto;
    }

    @PostMapping("/changeBookStatus/slug")
    public String handleChangeBookStatus(@RequestParam("slug") String slug,
                                         @RequestParam("status") String status,
                                         HttpServletResponse response, Model model,
                                         HttpServletRequest request) {

        model.addAttribute("isCartEmpty", false);
        cookieService.addBookInCocci(request, response, slug, status);
        return status.equals("CART") ? "redirect:/books/cart" : "redirect:/books/postponed";
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model, HttpServletRequest request,
                           HttpServletResponse response) {
        boolean isCart = false;
        boolean isKept = false;
        String nonDisplayKEPT = "";
        String nonDisplayCART = "";
        model.addAttribute("ratingDto", starsDtoService.getRatingDto(slug));
        Book book = bookRepository.findBookBySlug(slug);
        List<GenreEntity> genreEntities = genreService.getGenresForBook(book.getId());
        model.addAttribute("slugBook", book);
        model.addAttribute("genres", genreEntities);
        String buffer = cookieService.getKeptOrCart(request, slug);
        if (buffer.equals("CARTContents")) {
            isCart = true;
            nonDisplayCART = "display:none";
        }
        if (buffer.equals("KEPTContents")) {
            isKept = true;
            nonDisplayKEPT = "display:none";
        }
        model.addAttribute("nonDisplayKEPT", nonDisplayKEPT);
        model.addAttribute("nonDisplayCART", nonDisplayCART);
        model.addAttribute("isKEPT", isKept);
        model.addAttribute("isCART", isCart);
        model.addAttribute("countInPostponed", cookieService.countBookInCookie(response, request, "KEPT") == null ? 0 : cookieService.countBookInCookie(response, request, "KEPT"));
        model.addAttribute("countInCart", cookieService.countBookInCookie(response, request, "CART") == null ? 0 : cookieService.countBookInCookie(response, request, "CART"));
        model.addAttribute("allComments", likeAndReviewService.reviewAndLikeDto(slug));
        return "/books/slug";
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
