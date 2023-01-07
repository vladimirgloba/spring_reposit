package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    FileValidator fileValidator;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());

        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("uploadedFile", new UploadedFile());
        model.addAttribute("removeByRegex",new RemoveByRegex());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                logger.info(error.getField() + " - " + error.getDefaultMessage());
            }

            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("uploadedFile", new UploadedFile());
            model.addAttribute("removeByRegex",new RemoveByRegex());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("uploadedFile", new UploadedFile());
            model.addAttribute("removeByRegex",new RemoveByRegex());
            return "book_shelf";
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
    }


    @RequestMapping(value = "/removeByRegex", method = RequestMethod.POST)
    public String byRegex(@Valid RemoveByRegex removeByRegex,BindingResult bindingResult, Model model)  {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("uploadedFile", new UploadedFile());
        return "book_shelf";
        } else {
bookService.deleteBooksByRegex(removeByRegex.getRegex());
            return "redirect:/books/shelf";
        }

    }


}
