package com.example.MyBookShopApp.Service;

import com.example.MyBookShopApp.Repository.GenreRepository;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.GenreIndexDto;
import com.example.MyBookShopApp.data.ParentGenre;
import com.example.MyBookShopApp.data.TagsMainDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public List<TagsMainDto> getListGenresForMainPage() {
        List<TagsMainDto> dtos = new ArrayList<>();
        List<Object[]> objects = genreRepository.getListMainPageDto();
        for (Object[] objects1 : objects) {
            TagsMainDto dto = new TagsMainDto();
            dto.setName(objects1[0].toString());
            dto.setCountBooks(Integer.parseInt(objects1[1].toString()));
            dto.setFontSize(fontSize(Integer.parseInt(objects1[1].toString())));
            dtos.add(dto);
        }
        return dtos;
    }

    private int fontSize(Integer count) {
        List<Object[]> objects = genreRepository.getListMaxMin();
        Integer max = Integer.parseInt(objects.get(0)[1].toString());
        Integer min = Integer.parseInt(objects.get(0)[0].toString());
        int c = (max - min) / 16;
        int size = ((count - min) / c) + 16;
        return size;
    }

    private List<Integer> listIdForGenreIndexDtoFirstLevel() {
        return genreRepository.idParentFirstLevel();
    }


    public List<ParentGenre> parentLevelListDtoByNameGenre(Integer idParent){
        List<ParentGenre> parentGenreList = new ArrayList<>();

            ParentGenre parentGenre = new ParentGenre();
            List<GenreIndexDto> dtos = new ArrayList<>();
            GenreIndexDto dto = new GenreIndexDto();
            dto.setNameGenre(genreRepository.findById(idParent).get().getName());
            dto.setCountBooks(genreRepository.parentLevelCountBook(dto.getNameGenre()));
            parentGenre.setParent(dto);
            parentGenre.setSecondLevel(levelGenres(parentGenre.getParent().getNameGenre()));
            parentGenre.setThirdLevel(secondLevelParentMap(dto.getNameGenre()));
            if(!parentGenre.getThirdLevel().isEmpty()){
                Integer i=0;
                for(Map.Entry<GenreIndexDto,List<GenreIndexDto>> entry:parentGenre.getThirdLevel().entrySet()){
                    i=i+entry.getKey().getCountBooks();
                }
                dto.setCountBooks(dto.getCountBooks()+i);
                parentGenre.setParent(dto);
            }
            parentGenreList.add(parentGenre);

        return parentGenreList;

    }
    public List<ParentGenre> parentLevelListDto() {
        List<ParentGenre> parentGenreList = new ArrayList<>();
        for (Integer idParent : listIdForGenreIndexDtoFirstLevel()) {
            ParentGenre parentGenre = new ParentGenre();
            List<GenreIndexDto> dtos = new ArrayList<>();
            GenreIndexDto dto = new GenreIndexDto();
            dto.setNameGenre(genreRepository.findById(idParent).get().getName());
            dto.setCountBooks(genreRepository.parentLevelCountBook(dto.getNameGenre()));
            parentGenre.setParent(dto);
            parentGenre.setSecondLevel(levelGenres(parentGenre.getParent().getNameGenre()));
            parentGenre.setThirdLevel(secondLevelParentMap(dto.getNameGenre()));
            if(!parentGenre.getThirdLevel().isEmpty()){
                Integer i=0;
                for(Map.Entry<GenreIndexDto,List<GenreIndexDto>> entry:parentGenre.getThirdLevel().entrySet()){
                    i=i+entry.getKey().getCountBooks();
                }
                   dto.setCountBooks(dto.getCountBooks()+i);
                parentGenre.setParent(dto);
            }
            parentGenreList.add(parentGenre);
        }
        return parentGenreList;
    }

    List<GenreIndexDto> levelGenres(String parentGenreName) {
        List<GenreIndexDto> dtos = new ArrayList<>();
        List<Object[]> objects = genreRepository.listGenreAndCountBooks(parentGenreName);
        for (Object[] line : objects) {
            GenreIndexDto dto = new GenreIndexDto();
            dto.setNameGenre(line[0].toString());
            dto.setCountBooks(Integer.parseInt(line[1].toString()));
            dtos.add(dto);
        }
        return dtos;
    }

    Map<GenreIndexDto, List<GenreIndexDto>> secondLevelParentMap(String parentName) {
        Map<GenreIndexDto, List<GenreIndexDto>>buffer=new HashMap<>();
        List<GenreIndexDto>secondParent=parentSecondLevelList(parentName);
        if(secondParent.size()>0){
            for(GenreIndexDto dto:secondParent){
                buffer.put(dto,levelGenres(dto.getNameGenre()));
            }
        }
                return buffer;
    }

    List<GenreIndexDto> parentSecondLevelList(String nameParent) {
        List<Object[]> objects = genreRepository.listParentSecondLevel(nameParent);
        List<GenreIndexDto> buffer = new ArrayList<>();
        if (objects.size() > 0) {
            for (Object[] line : objects) {
                GenreIndexDto dto = new GenreIndexDto();
                dto.setNameGenre(line[0].toString());
                dto.setCountBooks(Integer.parseInt(line[1].toString()));
                buffer.add(dto);
            }
        }
        return buffer;
    }
public String getParentByGenreName(String genreName){
        return genreRepository.getParentNameByGenre(genreName);
}

public Integer isParent(String genreName){
        return genreRepository.selectIdIsParent(genreName);
}
}
