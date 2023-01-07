package com.example.MyBookShopApp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "book_rating")
@ApiModel(description = "data model of review & like")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "user id",position = 1)
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "user id",position = 4)
    private Integer userId;

    @Column(name = "book_id")
    @ApiModelProperty(value = "book id",position = 2)
    private Integer bookId;

    @Column(name = "stars")
    @ColumnDefault(value = "0")
    @ApiModelProperty(value = "stars",position = 6)
    private Integer stars;

    public RatingEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
