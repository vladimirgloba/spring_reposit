package com.example.MyBookShopApp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review")
@ApiModel(description = "data model of review")
public class BookReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "user id",position = 1)
    private Integer id;

    @Column(name = "book_id")
    @ApiModelProperty(value = "book id",position = 2)
    private Integer bookId;

    @Column(name = "text",columnDefinition = "TEXT")
    @ApiModelProperty(value = "user id",position = 3)
    private String text;

    @Column(name="time",columnDefinition = " timestamp NOT NULL")
    @ApiModelProperty(value = "registration time",position = 4)
    private LocalDateTime time;

    @Column(name = "user_id")
    @ApiModelProperty(value = "user id",position = 5)
    private Integer userId;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BookReviewEntity() {
    }
}
