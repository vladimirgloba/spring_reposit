package com.example.MyBookShopApp.data;

import java.util.List;
import java.util.Map;

public class ParentGenre {
    private GenreIndexDto parent;
    private List<GenreIndexDto> secondLevel;
    private Map<GenreIndexDto,List<GenreIndexDto>> thirdLevel;
    private boolean flag=false;

    public ParentGenre() {
    }

    public GenreIndexDto getParent() {
        return parent;
    }

    public void setParent(GenreIndexDto parent) {
        this.parent = parent;
    }

    public List<GenreIndexDto> getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(List<GenreIndexDto> secondLevel) {
        this.secondLevel = secondLevel;
    }

    public Map<GenreIndexDto, List<GenreIndexDto>> getThirdLevel() {
        return thirdLevel;
    }

    public void setThirdLevel(Map<GenreIndexDto, List<GenreIndexDto>> thirdLevel) {
        this.thirdLevel = thirdLevel;
        if(!thirdLevel.isEmpty())flag=true;
    }
}
