package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.repository.Book2UserRepository;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.service.Book2UserService;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.TagService;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.TagsMainDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TagController {

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

    public TagController() {
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
   public List<TagsMainDto> genresMainDtoList() {
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

@ModelAttribute("textContent")
String textContent(){return "#tags";}
    private String nameOfTag;


    @GetMapping("/indexTags")
    public String redirectMainPage() {
        return "index";
    }

    @GetMapping("/tags")
    public String getTagsIndex(Model model, @RequestParam("tagName") String tagName) {
        model.addAttribute("tagName", tagName);
        model.addAttribute("booksList", booksList(tagName));
        nameOfTag=tagName;
        return "/tags/index";
    }
    private List<Book> booksList(String tagName){
        return bookRepository.getBooksByTagName(tagName,20,0);
}
    @GetMapping("/books/tag/10")
    @ResponseBody
    public BooksPageDto getNewBooksPage(Model model,@RequestParam("offset") Integer offset,
                                        @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookRepository.getBooksByTagName(nameOfTag,limit,offset));
    }


}
