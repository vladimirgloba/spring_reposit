package com.example.MyBookShopApp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review_like")
@ApiModel(description = "data model of review & like")
public class BookReviewLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "user id",position = 1)
    private Integer id;

    @Column(name = "review_id")
    @ApiModelProperty(value = "review id",position = 2)
    private Integer reviewId;

    @Column(name="time",columnDefinition = " timestamp NOT NULL")
    @ApiModelProperty(value = "registration time",position = 3)
    private LocalDateTime time;

    @Column(name = "user_id")
    @ApiModelProperty(value = "user id",position = 4)
    private Integer userId;


    @Column(name = "value")
    @ApiModelProperty(value = "value",position = 5)
    private int value;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BookReviewLikeEntity() {
    }
}
