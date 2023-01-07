package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.TagsMainDto;
import com.example.MyBookShopApp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public List<TagsMainDto> getListTagForMainPage() {
        List<TagsMainDto> dtos = new ArrayList<>();
        List<Object[]> objects = tagRepository.getListMainTagDto();
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
        List<Object[]> objects = tagRepository.getListMaxMin();
        Integer max = Integer.parseInt(objects.get(0)[1].toString());
        Integer min = Integer.parseInt(objects.get(0)[0].toString());
        int c = (max - min) / 16;
        int size = ((count - min) / c) + 16;
        return size;
    }
}
