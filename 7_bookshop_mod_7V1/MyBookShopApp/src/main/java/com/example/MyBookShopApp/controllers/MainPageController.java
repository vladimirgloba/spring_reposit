package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.errs.EmptySearchException;
import com.example.MyBookShopApp.repository.Book2UserRepository;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.service.Book2UserService;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.TagService;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.data.TagsMainDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    TagService tagService;
    @Autowired
    private BookService bookService;
    @Autowired
    private Book2UserRepository book2UserRepository;
    @Autowired
    private Book2UserService book2UserService;

    public MainPageController() {

    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        List<Object[]> objects = book2UserRepository.popularBooksId(6, 0);
        List<Book> bookList = new ArrayList<>();
        for (Object[] object : objects) {
            Book book = bookRepository.findById(Integer.valueOf(object[0].toString())).get();
            bookList.add(book);
        }
        return bookList;
    }

    @ModelAttribute("tagsMainDtoList")
    List<TagsMainDto> genresMainDtoList() {
        return tagService.getListTagForMainPage();
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.getPageOfRecentBooks(0, 6).getContent();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }


    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit) {

        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }


    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit) {
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
        return new BooksPageDto(bookList);
    }

    @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto getNewBooksPage(@RequestParam(value = "from", required = false,
            defaultValue = "") String from,
                                        @RequestParam(value = "to", required = false,
                                                defaultValue = "") String to,
                                        @RequestParam("offset") Integer offset,
                                        @RequestParam("limit") Integer limit) {
        if(from.isEmpty()||to.isEmpty()){
           return new BooksPageDto(bookService.getPageOfRecentBooks(offset, limit).getContent());
        }
        else {
            from = from.replaceAll("\\.", "-");
            to = to.replaceAll("\\.", "-");
            String[] buffer = from.split("-");
            from = buffer[2] + "-" + buffer[1] + "-" + buffer[0];
            buffer = to.split("-");
            to = buffer[2] + "-" + buffer[1] + "-" + buffer[0];
            Date before = Date.valueOf(from);
            Date after = Date.valueOf(to);
            return new BooksPageDto(bookService.sortedListBooksByDate(before, after, limit, offset));
        }
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                   Model model) throws EmptySearchException {
        if (searchWordDto != null) {
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResults",
                    bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 5).getContent());
            return "/search/index";
        } else {
            throw new EmptySearchException("Поиск по null невозможен");
        }
    }


    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), offset, limit).getContent());
    }

}
