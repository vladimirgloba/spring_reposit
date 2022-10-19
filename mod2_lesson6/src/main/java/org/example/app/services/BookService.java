package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;
    private final Logger logger = Logger.getLogger(BookService.class);


    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean existsAuthor(String authorName){
        return bookRepo.selectBookByAuthor(authorName);
    }
    public boolean existsTitle(String title){
        return bookRepo.selectBookByTitle(title);
    }
    public boolean existsSize(int size){
        return bookRepo.selectBookBySize(size);
    }
public boolean existsBookById(Integer id){
        return bookRepo.searchBookById(id);
}
public void deleteBooksByRegex(String regex){
        bookRepo.removeByRegex(regex);
}

    public void defaultInit() {
        logger.info("default INIT in book service");
    }

    public void defaultDestroy() {
        logger.info("default DESTROY in book service");
    }
}
