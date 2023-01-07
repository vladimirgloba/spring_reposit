package com.example.MyBookShopApp.Service;

import com.example.MyBookShopApp.Repository.BookRepository;
import com.example.MyBookShopApp.data.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;



    public BookService() {

    }

    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    //NEW BOOK SEVICE METHODS

    public List<Book> getBooksByAuthor(String authorName){
        return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }
public List<Book> sortedListBooksByDate(Date before, Date after, Integer limit, Integer offset){
    return     bookRepository.sortedListByDate(before, after,limit,offset);
}
    public List<Book> getBooksByTitle(String title){
        return bookRepository.findBooksByTitleContaining(title);
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max){
        return bookRepository.findBooksByPriceOldBetween(min,max);
    }

    public List<Book> getBooksWithPrice(Integer price){
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxPrice(){
        return bookRepository.getBooksWithMaxDiscount();
    }

    public List<Book> getBestsellers(){
        return bookRepository.getBestsellers();
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<Book>getPageOfRecentBooks(Integer offset,Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return  bookRepository.newBooks(nextPage);
    }
public Page<Book> getPageOfBestseller(Integer offset, Integer limit){
        Pageable nextPage=PageRequest.of(offset,limit);
        return bookRepository.bestsellersList(nextPage);
}
    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        System.out.println("offset = "+offset+" limit = "+limit);
        return bookRepository.findBookByTitleContaining(searchWord,nextPage);
    }
    public List<Book> finedBooksByGenreName(String nameGenre,Integer limit, Integer offset){
        return bookRepository.selectBookByGenreName(nameGenre,limit,offset);
    }

    public List<Book>finedBookByAuthorId(Integer authorId,Integer limit, Integer offset){
        return bookRepository.finedBooksByAuthorId(authorId,limit,offset);
    }
}
