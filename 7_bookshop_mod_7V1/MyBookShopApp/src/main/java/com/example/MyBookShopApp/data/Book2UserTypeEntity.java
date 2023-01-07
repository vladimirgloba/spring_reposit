package com.example.MyBookShopApp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "book2user_type")
@ApiModel(description = "data model of binding of books to type")
public class Book2UserTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id of the type of binding of books to users",position = 1)
    private Integer id;
    @ApiModelProperty(value = "binding code",position = 2)
    private String code;
    @ApiModelProperty(value = "binding code",position = 3)
    private String name;

    public Book2UserTypeEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
