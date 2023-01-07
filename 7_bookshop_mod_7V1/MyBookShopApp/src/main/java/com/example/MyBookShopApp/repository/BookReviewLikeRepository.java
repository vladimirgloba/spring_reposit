package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewLikeRepository extends JpaRepository<BookReviewLikeEntity,Integer> {
@Query(value = "select count(id) from book_review_like where review_id = ?1 and user_id=?2",nativeQuery = true)
    Integer finedLikeByUserIdAndReviewId(Integer reviewId, Integer userId);
@Query(value = "select max(id) from  book_review_like",nativeQuery = true)
    Integer maxIdValue();

@Query(value = "select count(id) from book_review_like where value = ?2 and review_id = ?1",nativeQuery = true)
    Integer countLike(Integer reviewId, Integer value);
}
