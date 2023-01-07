package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.Repository.BookRepository;
import com.example.MyBookShopApp.Service.GenreService;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.GenreIndexDto;
import com.example.MyBookShopApp.data.ParentGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GenresController {
    @Autowired
    GenreService genreService;

    @Autowired
    BookRepository bookRepository;

    @ModelAttribute("parentGenres")
    List<ParentGenre> parentGenres(){return  genreService.parentLevelListDto();}
    @GetMapping("/genres")
    public String genresPage(){
        return "/genres/index";
    }
private String genreName;
    @GetMapping("/genresSlug")
    public String genresSlugController(Model model, @RequestParam("tagName") String tagName) {
        genreName=tagName;
        Integer flag=genreService.isParent(tagName);
        if(flag==null){
        model.addAttribute("genreParentName", genreService.getParentByGenreName(tagName));
        model.addAttribute("genreName", tagName);
        model.addAttribute("bookList", bookRepository.selectBookByGenreName(tagName,20,0));
        return "/genres/slug";}
        else {
           model.addAttribute("parentGenres",genreService.parentLevelListDtoByNameGenre(flag));
            return "/genres/index";
        }
    }

    @GetMapping("books/genre/1020")
    @ResponseBody
    public BooksPageDto getNewBooksPage(Model model, @RequestParam("offset") Integer offset,
                                        @RequestParam("limit") Integer limit) {

        return new BooksPageDto(bookRepository.selectBookByGenreName(genreName,limit,offset));
    }
}
