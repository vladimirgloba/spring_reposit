package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.BookReviewEntity;
import com.example.MyBookShopApp.data.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.RatingDto;
import com.example.MyBookShopApp.data.RatingEntity;
import com.example.MyBookShopApp.repository.*;
import com.github.javafaker.Bool;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class StarsDtoService {
    @Autowired
    private RatingEntityRepository ratingEntityRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    EntityManagerRepository em;

    private Map<Integer, Integer> starValue;

    public StarsDtoService() {
    }

    public boolean insertOrSetRating(String slug, int value,Integer userId) {
        if(ratingEntityRepository.finedRatingByBookAndUser(slug,userId)==null){
            RatingEntity ratingEntity = new RatingEntity();
            ratingEntity.setId(ratingEntityRepository.maxIdFromBookRating()+1);
            ratingEntity.setStars(value);
            ratingEntity.setBookId(bookRepository.findBookBySlug(slug).getId());
            ratingEntity.setUserId(userId);
            ratingEntityRepository.save(ratingEntity);
            System.out.println("insert in likes");
        }
        else{
            String sql="update book_rating set stars = "+value+" where book_id=(select id from books " +
                    "where slug = '"+slug+"') and user_id = "+userId+";";
            em.updateDB(sql);

        }

        return true;
    }

    public Map<Integer, Integer> starsAndValue(String slug) {

        Map<Integer, Integer> buffer = new HashMap<>();
       int i=1;
       while (i<=5){
          buffer.put(i, ratingEntityRepository.countStars(i,slug)==null?0:ratingEntityRepository.countStars(i,slug));
          i++;
       }
        starValue=new HashMap<>();
       starValue.putAll(buffer);
       return buffer;
    }

    public int avgValueStar() {
        Integer bufferInt = 0;
        Integer bufferSum = 0;
        if (!starValue.isEmpty()) {
            for (Map.Entry<Integer, Integer> entry : starValue.entrySet()) {
                bufferInt = bufferInt + (entry.getKey() * entry.getValue());
                bufferSum = bufferSum + entry.getValue();
            }

            return bufferSum==0?0:Math.toIntExact(Math.round(bufferInt / bufferSum));
        } else return 0;
    }

    public List<Boolean> starList(int countActiveStar) {
        List<Boolean> buffer = new ArrayList<>();

        int size = countActiveStar;
        while (size > 0) {
            buffer.add(true);
            size--;
        }
        size = 5 - countActiveStar;
        while (size > 0) {
            buffer.add(false);
            size--;
        }
        return buffer;
    }

    public RatingDto getRatingDto(String slug) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setMapStarAndCount(starsAndValue(slug));
        Integer buffer = 0;
        ratingDto.setRealStars(starList(avgValueStar()));
        ratingDto.setFirstRow(starList(5));
        ratingDto.setSecondRow(starList(4));
        ratingDto.setThirdRow(starList(3));
        ratingDto.setForRow(starList(2));
        ratingDto.setFiveRow(starList(1));
        Map  <Integer, Integer> bufferMap=new HashMap(sortByKey(ratingDto.getMapStarAndCount()));
        List<Integer>bufferList=new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry:bufferMap.entrySet()){
            bufferList.add(entry.getValue());
            buffer=buffer+entry.getValue();
        }
        ratingDto.setAllRatingCount(buffer);
        ratingDto.setResultList(bufferList);
        return ratingDto;
    }

    private <K extends Comparable, V> Map<K, V> sortByKey(Map<K, V> map) {
        Map<K, V> treeMap = new TreeMap<>(new Comparator<K>() {
            @Override
            public int compare(K a, K b) {
                return a.compareTo(b);
            }
        });
        treeMap.putAll(map);
        return treeMap;
    }
}

