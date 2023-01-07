package com.example.MyBookShopApp.data;

import java.util.ArrayList;
import java.util.List;

public class ReviewAndLikeDto {
    private Integer countReview;
    private List<OneReviewDto>reviews;

    public ReviewAndLikeDto() {
    }

    public Integer getCountReview() {
        return countReview;
    }

    public void setCountReview(Integer countReview) {
        this.countReview = countReview;
    }

    public List<OneReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<OneReviewDto> reviews) {
        this.reviews = new ArrayList<>(reviews) ;
    }
}
