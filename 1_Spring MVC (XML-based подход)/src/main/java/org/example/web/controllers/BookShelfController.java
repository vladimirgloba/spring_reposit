package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {
    private final static Logger logger = Logger.getLogger(BookShelfController.class);
    @Autowired
    private BookService bookService;
    @Autowired
    public BookShelfController() {

    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@RequestParam(required = false, defaultValue = "author") String author,
                           @RequestParam(required = false, defaultValue = "title") String title,
                           @RequestParam(required = false, defaultValue = "size") String size) {
        if(isNumeric(size)&&author!=null&&author!=""&&title!=null&&title!=""){
            bookService.saveBook(new Book(author,title,Integer.parseInt(size)));
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }else {
            return "redirect:/books/shelf";
        }



    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") String inputString) {
        if(isNumeric(inputString)){
            Integer bookIdToRemove=Integer.valueOf(inputString);
            logger.info("Attempt to delete book id = " + inputString);
            if (bookService.removeBookById(bookIdToRemove)) {
                logger.info("delete book id = " + bookIdToRemove.toString());
                return "redirect:/books/shelf";
            } else {
                logger.info("not exists book id = " + inputString);
                return "redirect:/books/shelf";
            }
        }else{
            logger.info("not exists book with id " );
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByRegex")
    public String removeWithRegex(@RequestParam(required = false, value = "queryRegex") String inputString){
        if(inputString!=null&&inputString!=""){
            bookService.removeByRegex(inputString);
            return "redirect:/books/shelf";
        }
        else {
            logger.info("Check the input string" );
            return  "redirect:/books/shelf";
        }
    }



    public boolean isNumeric(String string) {
        Integer intValue;

        System.out.println(String.format("Parsing string: \"%s\"", string));

        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

}