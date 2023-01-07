package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReviewEntity,Integer> {
    @Query(value = "select * from book_review where book_id =" +
            "(select id from books where slug = ?1) order by book_review.id ",nativeQuery = true)
    List<BookReviewEntity> finedReviewBySlug(String slug);

    @Query(value = "select count(id) from book_review where book_id= +\n" +
            "(select id from books where slug=?1)",nativeQuery = true)
    Integer countReviewByBookSlug(String slug);

    @Query(value="select count(id) from  book_review where book_id =" +
            "(select id from books where slug = ?1) and user_id=?2",nativeQuery = true)
    Integer finedReviewByBookSlugAndUserId(String slug, Integer user_id);

    @Query(value = "select max(id) from book_review",nativeQuery = true)
    Integer maxId();
}
