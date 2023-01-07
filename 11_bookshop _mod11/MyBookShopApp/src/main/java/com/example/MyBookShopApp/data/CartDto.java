package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.security.BookstoreUser;

import java.text.DecimalFormat;
import java.util.List;

public class CartDto {
    private List<Book> bookList;
    private BookstoreUser user;
    private String many;
    private List<TransactionDto> transactionList;

    public CartDto() {
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public BookstoreUser getUser() {
        return user;
    }

    public void setUser(BookstoreUser user) {
        this.user = user;
    }

    public String getMany() {
        return many;
    }

    public void setMany(double many) {
        DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
        String result="На счёте ";
         result =result+ decimalFormat.format(many);
        result =result+" рублей";
        this.many = result;
    }

    public List<TransactionDto> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<TransactionDto> transactionList) {
        this.transactionList = transactionList;
    }
}
