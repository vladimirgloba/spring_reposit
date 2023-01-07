package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingEntityRepository extends JpaRepository<RatingEntity,Integer> {
    @Query(value = "select max (id) from book_rating",nativeQuery = true)
    Integer maxIdFromBookRating();

    @Query(value = "select count(id)  from book_rating where stars!=0 and stars=?1 and book_id = \n" +
            "(select id from books where  slug=?2)  ;",nativeQuery = true)
    Integer countStars(int countStars,String slug);

    @Query(value = "select stars from book_rating where book_id=(select id from books where  slug=?1) and user_id=?2",nativeQuery = true)
    Integer finedRatingByBookAndUser(String slug,Integer userId);



}
