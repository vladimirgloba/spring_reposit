package org.example.web.dto;

import javax.validation.constraints.*;

public class Book {
    private Integer id;
    @NotEmpty(message = "Author's name cannot be empty.")
    @Size(min=2, max=30, message = "the number of characters is from 2 to 30 in author")
    private String author;
    @NotEmpty(message = "Title cannot be empty.")
    @Size(min=2, max=30, message = "the number of characters is from 2 to 30 in title")
    private String title;
    @NotNull(message = "is empty field")
    @DecimalMin(value = "1.0", message = "invalid number < 0")
    @Digits(integer = 4, fraction = 0,message = "no more than 4 digits")
    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                '}';
    }
}
