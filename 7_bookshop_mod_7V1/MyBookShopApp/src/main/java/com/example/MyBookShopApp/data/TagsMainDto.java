package com.example.MyBookShopApp.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TagsMainDto {
    private String name;
    private Integer countBooks;
    private int fontSize;

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public TagsMainDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountBooks() {
        return countBooks;
    }

    public void setCountBooks(Integer countBooks) {
        this.countBooks = countBooks;
    }
}
