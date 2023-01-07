package com.example.MyBookShopApp.data;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Book {

    private Integer id;
    private Integer authorId;
    private String title;
    private Integer priceOld;
    private Integer price;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + authorId.toString() + '\'' +
                ", title='" + title + '\'' +
                ", priceOld=" + priceOld +
                ", price=" + price +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(Integer priceOld) {
        this.priceOld = priceOld;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
