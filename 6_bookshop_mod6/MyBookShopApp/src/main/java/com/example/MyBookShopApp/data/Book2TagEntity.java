package com.example.MyBookShopApp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "book2tag")
@ApiModel(description = "data model of binding of books to tag")
public class Book2TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id of the type of binding of books to tags",position = 1)
    private Integer id;

    @Column(name="book_id",columnDefinition = "INT NOT NULL")
    @ApiModelProperty(value = "book id",example = "1234567",position = 2)
    private Integer bookId;

    @Column(name="tag_id",columnDefinition = "INT NOT NULL")
    @ApiModelProperty(value = "genre id",example = "1234567",position = 3)
    private Integer tagId;

    public Book2TagEntity() {
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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
