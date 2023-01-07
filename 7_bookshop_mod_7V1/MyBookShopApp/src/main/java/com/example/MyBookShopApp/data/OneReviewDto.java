package com.example.MyBookShopApp.data;

import java.time.LocalDateTime;
import java.util.List;

public class OneReviewDto {
    private UserEntity user;
    private  String firstText;
    private String SecondText;
    private Integer  countLike;
    private Integer countDislike;
    private List<Boolean> stars;

    private Integer bookReviewId;
    String time;

    public String getTime() {
        return time;
    }

    public Integer getBookReviewId() {
        return bookReviewId;
    }



    public void setBookReviewId(Integer bookReviewId) {
        this.bookReviewId = bookReviewId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OneReviewDto() {
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    public String getFirstText() {
        return firstText;
    }

    public void setFirstText(String firstText) {
        this.firstText = firstText;
    }

    public String getSecondText() {
        return SecondText;
    }

    public void setSecondText(String secondText) {
        SecondText = secondText;
    }

    public Integer getCountLike() {
        return countLike;
    }

    public void setCountLike(Integer countLike) {
        this.countLike = countLike;
    }

    public Integer getCountDislike() {
        return countDislike;
    }

    public void setCountDislike(Integer countDislike) {
        this.countDislike = countDislike;
    }

    public List<Boolean> getStars() {
        return stars;
    }

    public void setStars(List<Boolean> stars) {
        this.stars = stars;
    }
}
