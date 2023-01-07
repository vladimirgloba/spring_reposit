package com.example.MyBookShopApp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "book2genre")
@ApiModel(description = "data model of binding of books to genre")
public class Book2GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id of the type of binding of books to genres",position = 1)
    private Integer id;

    @Column(name="book_id",columnDefinition = "INT NOT NULL")
    @ApiModelProperty(value = "book id",example = "1234567",position = 2)
    private Integer bookId;

    @Column(name="genre_id",columnDefinition = "INT NOT NULL")
    @ApiModelProperty(value = "genre id",example = "1234567",position = 3)
    private Integer genreId;

    public Book2GenreEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }
}
