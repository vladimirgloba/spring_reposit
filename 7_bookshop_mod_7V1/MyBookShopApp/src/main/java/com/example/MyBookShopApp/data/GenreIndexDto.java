package com.example.MyBookShopApp.data;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("prototype")
public class GenreIndexDto {
    private String nameGenre;
    private Integer countBooks;

    public GenreIndexDto() {
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }

    public Integer getCountBooks() {
        return countBooks;
    }

    public void setCountBooks(Integer countBooks) {
        this.countBooks = countBooks;
    }
}
