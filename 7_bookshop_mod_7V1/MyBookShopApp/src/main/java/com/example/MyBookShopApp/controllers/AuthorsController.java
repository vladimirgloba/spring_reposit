package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.repository.AuthorRepository;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.service.AuthorService;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
public class AuthorsController {

    private final AuthorService authorService;
    private final AuthorRepository authorRepository;
    private final BookService bookService;

    @Autowired
    public AuthorsController(AuthorService authorService,AuthorRepository authorRepository,BookService bookService) {
        this.authorService = authorService;
        this.authorRepository=authorRepository;
        this.bookService=bookService;
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("authorsMap")
    public Map<String,List<Author>> authorsMap(){
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors")
    public String authorsPage(){
        return "/authors/index";
    }

    @ApiOperation("method to get map of authors")
    @GetMapping("/api/authors")
    @ResponseBody
    public Map<String,List<Author>> authors(){
        return authorService.getAuthorsMap();
    }
private Integer Id;

        @GetMapping("/authors/slug")
        public String authorsSlug(Model model, @RequestParam("authorId")
                Integer authorId){
            Id=authorId;
        Author author=authorRepository.findById(authorId).get();
model.addAttribute("author",author);
List<List<String>> list=forDescription(descriptions(authorRepository.findById(authorId).get()));
model.addAttribute("photo",author.getPhoto());
model.addAttribute("listStringNoHiden",list.get(0));
model.addAttribute("listStringHiden",list.get(1));
model.addAttribute("bookList",bookService.finedBookByAuthorId(authorId,10,0));
        return "/authors/slug";
    }

    @GetMapping("/books/author")
    public String authorsBooks(Model model, @RequestParam("authorId")
            Integer authorId){
            Id=authorId;
            model.addAttribute("author", authorRepository.findById(authorId).get());
        model.addAttribute("bookList",bookService.finedBookByAuthorId(authorId,20,0));
        return "books/author";
    }
    @GetMapping("/books/author/20")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("limit") Integer limit,
    @RequestParam("offset") Integer offset) {

        return new BooksPageDto(bookService.finedBookByAuthorId(Id,limit,offset));
    }

    private List<String>descriptions(Author author){
        String[] words = author.getDescription().split(" ");
        List<String>buffer=new ArrayList<>();
        int i=1;
        String entry="";
        for(String str:words){
            if((int)i%9!=0){
                entry=entry+" "+str;i++;
            }else{
                buffer.add(entry);
                entry=str;i++;
            }
        }
        return buffer;
    }
   List<List<String>> forDescription(List<String>stringList){
       List<List<String>>mainBuffer=new ArrayList<>();
        List<String>buffer=new ArrayList<>();
       List<String>buffer1=new ArrayList<>();
        buffer.add(stringList.get(0));
        buffer.add(stringList.get(1));
        buffer.add(stringList.get(2));

        stringList.remove(0);
       stringList.remove(1);
       stringList.remove(2);

       for(String str:stringList){
           buffer1.add(str);
       }
                mainBuffer.add(buffer);
       mainBuffer.add(buffer1);
       return mainBuffer;
    }
}
