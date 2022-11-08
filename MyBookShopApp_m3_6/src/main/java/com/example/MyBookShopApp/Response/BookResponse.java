package com.example.MyBookShopApp.Response;

import com.example.MyBookShopApp.data.Author;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Getter
@Setter
@NoArgsConstructor
@Component
@Scope("prototype")
public class BookResponse {

    private Integer id;
    private Author author;
    private String title;
    private Integer priceOld;
    private Integer price;

    public BookResponse(Integer id, Author author, String title, Integer priceOld, Integer price) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.priceOld = priceOld;
        this.price = price;
    }
}

