package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LikeAndReviewService {
    @Autowired
    BookReviewRepository bookReviewRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookReviewLikeRepository bookReviewLikeRepository;

    @Autowired
    RatingEntityRepository ratingEntityRepository;

    @Autowired
    EntityManagerRepository em;

    public ReviewAndLikeDto reviewAndLikeDto(String slug) {
        ReviewAndLikeDto buffer = new ReviewAndLikeDto();
        buffer.setCountReview(bookReviewRepository.countReviewByBookSlug(slug));
        buffer.setReviews(oneReviewDtoList(slug));
        return buffer;
    }
public boolean insertOrUpdateComments(String slug, Integer userId, String text){
        if(bookReviewRepository.finedReviewByBookSlugAndUserId(slug,userId)>0){
            String sql="update book_review set text='"+text+"', time = '"+ Timestamp.valueOf(LocalDateTime.now()) +"' where book_id=" +
                    "(select id from books where slug='"+slug+"') and user_id = "+userId;
            System.out.println(sql);
            em.updateDB(sql);
        }
        else{
            BookReviewEntity entity=new BookReviewEntity();
            entity.setId(bookReviewRepository.maxId()+1);
            entity.setTime(LocalDateTime.now());
            entity.setText(text);
            entity.setUserId(userId);
            entity.setBookId(bookRepository.findBookBySlug(slug).getId());
            bookReviewRepository.save(entity);
        }
        return true;
}
    private List<OneReviewDto> oneReviewDtoList(String slug) {
        List<OneReviewDto> oneReviewDtoList = new ArrayList<>();
        List<BookReviewEntity> bookReviewEntityList = new ArrayList<>(bookReviewRepository.finedReviewBySlug(slug));
        for (BookReviewEntity entity : bookReviewEntityList) {
            OneReviewDto dto = new OneReviewDto();
           dto.setBookReviewId(entity.getId());
            List<String> str = list(entity.getText());
            dto.setFirstText(str.get(0));
            dto.setSecondText(str.get(1));
            try {
                dto.setCountLike(bookReviewLikeRepository.countLike(entity.getId(), 1));
            }catch (Exception e){ dto.setCountLike(0);}
            try {
                dto.setCountDislike(bookReviewLikeRepository.countLike(entity.getId(), -1));
            }catch (Exception e){ dto.setCountLike(0);}
            dto.setUser(getUser(entity.getUserId()));
            dto.setStars(starList(slug, dto.getUser().getId()));
//            17.04.2020 16:40
            String days=entity.getTime().getDayOfMonth()<10?"0"+entity.getTime().getDayOfMonth(): String.valueOf(entity.getTime().getDayOfMonth());
            String month=(entity.getTime().getMonthValue() + 1)<10?"0"+(entity.getTime().getMonthValue()): String.valueOf((entity.getTime().getMonthValue() + 1));
            dto.setTime(days + "." +
                    month + "." +
                    entity.getTime().getYear() + " " +
                    entity.getTime().getHour() + ":" +
                    entity.getTime().getMinute());
            oneReviewDtoList.add(dto);

        }
        return oneReviewDtoList;
    }
public boolean setOrInsertLike(Integer reviewId, Integer userId,int value){
        try {
            if (bookReviewLikeRepository.finedLikeByUserIdAndReviewId(reviewId, userId) == 0) {
                BookReviewLikeEntity likeEntity = new BookReviewLikeEntity();
                likeEntity.setReviewId(reviewId);
                likeEntity.setValue(value);
                likeEntity.setId(bookReviewLikeRepository.maxIdValue() + 1);
                likeEntity.setTime(LocalDateTime.now());
                likeEntity.setUserId(userId);
                bookReviewLikeRepository.save(likeEntity);
            } else {

                String sql = "update book_review_like set value = " + value + " where user_id = " + userId + " and review_id = " + reviewId;
                em.updateDB(sql);
            }
            return true;
        }catch (Exception e){
            return false;
        }
}
    private List<String> list(String str) {
        List<String> list = Arrays.asList(str.split(" "));
        String str1 = "";
        String str2 = "";
        int i = 0;
        int cons=list.size()<650?list.size():650;
        while (str1.length() < cons) {
            str1 = str1 + list.get(i) + " ";
            i++;
        }
        while (i < list.size()) {
            str2 = str2 + list.get(i) + " ";
            i++;
        }
        List<String> buffer = new ArrayList<>();
        buffer.add(str1.substring(0, str1.length() - 2));
        buffer.add(str2.length() > 4 ? str2.substring(0, str2.length() - 2) : "");
        return buffer;
    }

    private List<Boolean> starList(String slug, Integer userId) {

        int countFullStars = 0;
        try{

            countFullStars=ratingEntityRepository.finedRatingByBookAndUser(slug,userId);
        }
        catch (Exception e){

        }
        int countEmptyStars = 5 -  countFullStars;
        List<Boolean> buffer = new ArrayList<>();
        while (countFullStars > 0) {
            buffer.add(true);
            countFullStars--;
        }
        while (countEmptyStars > 0) {
            buffer.add(false);
            countEmptyStars--;
        }
        return buffer;
    }

    private UserEntity getUser(Integer userId) {
        return userRepository.findById(userId).get();
    }


}
