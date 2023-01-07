package com.example.MyBookShopApp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "genre")
@ApiModel(description = "data model of genres")
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id of the genre",position = 1)
    private Integer id;

    @Column(name = "parent_id")
    @ApiModelProperty(value = "id of parent genre",example = "12324",position = 2)
    private Integer parentId;

    @Column(name = "name")
    @ApiModelProperty(value = "name of genre",example = "modern prose",position = 3)
    private String name;

    @Column(name = "slug")
    @ApiModelProperty(value = "mnemonical identity sequence of characters",position = 4)
    private String slug;

    public GenreEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
