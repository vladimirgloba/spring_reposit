package com.example.MyBookShopApp.dataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;
}
