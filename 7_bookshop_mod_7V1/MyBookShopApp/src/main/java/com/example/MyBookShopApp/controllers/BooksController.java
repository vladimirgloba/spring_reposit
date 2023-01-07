package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.service.CookieService;
import com.example.MyBookShopApp.service.GenreService;
import com.example.MyBookShopApp.service.ResourceStorage;
import com.example.MyBookShopApp.service.StarsDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

@Controller
public class BooksController {

    private final BookRepository bookRepository;
    private final ResourceStorage storage;
    private final GenreService genreService;
    private final CookieService cookieService;
    private final StarsDtoService starsDtoService;

    @Autowired
    public BooksController(BookRepository bookRepository, ResourceStorage storage, GenreService genreService, CookieService cookieService, StarsDtoService starsDtoService) {
        this.bookRepository = bookRepository;
        this.storage = storage;
        this.genreService = genreService;
        this.cookieService = cookieService;
        this.starsDtoService = starsDtoService;
    }


    @PostMapping("/books/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {

        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookRepository.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate);
        return ("redirect:/books/" + slug);
    }

    @GetMapping("/books/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {

        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: " + data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }


}