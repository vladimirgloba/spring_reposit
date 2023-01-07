package com.example.MyBookShopApp.data;

import java.util.List;

public class ObjectPageDto {
    private Integer count;
    private List<Object[]> objects;

    public ObjectPageDto(List<Object[]> objects) {
        this.objects = objects;
        this.count = objects.size();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Object[]> getObjects() {
        return objects;
    }

    public void setObjects(List<Object[]> objects) {
        this.objects = objects;
    }
}
