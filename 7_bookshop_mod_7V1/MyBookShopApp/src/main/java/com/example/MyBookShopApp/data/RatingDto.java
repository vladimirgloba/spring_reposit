package com.example.MyBookShopApp.data;

import io.swagger.models.auth.In;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingDto {
    private Map<Integer, Integer> mapStarAndCount;
    private List<Boolean> realStars;
    private Integer allRatingCount;
    private List<Boolean> firstRow;
    private List<Boolean> secondRow;
    private List<Boolean> thirdRow;
    private List<Boolean> forRow;
    private List<Boolean> fiveRow;
    private List<Integer> resultList;

    public RatingDto() {
    }

    public Map<Integer, Integer> getMapStarAndCount() {
        return mapStarAndCount;
    }

    public void setMapStarAndCount(Map<Integer, Integer> mapStarAndCount) {
        this.mapStarAndCount = new HashMap<>();
        this.mapStarAndCount.putAll(mapStarAndCount);
    }

    public List<Boolean> getRealStars() {
        return realStars;
    }

    public void setRealStars(List<Boolean> realStars) {
        this.realStars = realStars;
    }

    public Integer getAllRatingCount() {
        return allRatingCount;
    }

    public void setAllRatingCount(Integer allRatingCount) {
        this.allRatingCount = allRatingCount;
    }

    public List<Boolean> getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(List<Boolean> firstRow) {
        this.firstRow = firstRow;
    }

    public List<Boolean> getSecondRow() {
        return secondRow;
    }

    public void setSecondRow(List<Boolean> secondRow) {
        this.secondRow = secondRow;
    }

    public List<Boolean> getThirdRow() {
        return thirdRow;
    }

    public void setThirdRow(List<Boolean> thirdRow) {
        this.thirdRow = thirdRow;
    }

    public List<Boolean> getForRow() {
        return forRow;
    }

    public void setForRow(List<Boolean> forRow) {
        this.forRow = forRow;
    }

    public List<Boolean> getFiveRow() {
        return fiveRow;
    }

    public void setFiveRow(List<Boolean> fiveRow) {
        this.fiveRow = fiveRow;
    }

    public List<Integer> getResultList() {
        return resultList;
    }

    public void setResultList(List<Integer> resultList) {
        this.resultList = resultList;
    }
}


