package org.example.web.controllers;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class UpLoadController {
    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;
    @Autowired
    FileValidator fileValidator;

    public UpLoadController(BookService bookService) {
        this.bookService = bookService;
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String upload(@Valid UploadedFile file,
                         BindingResult result,
                         RedirectAttributes redirectMap, Model model) throws IOException {
        fileValidator.validate(file, result);
        if (result.hasErrors()) {
            System.out.println("error");
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("removeByRegex", new RemoveByRegex());
            return "book_shelf";
        } else {
            System.out.println("no error");
            MultipartFile multipartFile = file.getFile();
            InputStream in = multipartFile.getInputStream();
            File destination = new File(System.getProperty("catalina.home") + multipartFile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(in, destination);
//            redirectMap.addFlashAttribute("filename", multipartFile.getOriginalFilename());
            return "redirect:/books/shelf";
        }

    }
}
