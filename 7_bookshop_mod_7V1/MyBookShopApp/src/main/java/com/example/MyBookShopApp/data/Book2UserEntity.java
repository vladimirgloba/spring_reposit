package com.example.MyBookShopApp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book2user")
@ApiModel(description = "data model of binding of books to users")
public class Book2UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id of the type of binding of books to users",position = 1)
    private Integer id;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    @ApiModelProperty(value = "time of binding the books to users",example = "2014-04-04 20:32:59.390583+02",position = 2)
    private LocalDateTime time;

    @Column(name="type_id", columnDefinition = "INT NOT NULL")
    @ApiModelProperty(value = "code of type of binding of books to users",example = "1",position = 3)
    private int typeId;

    @Column(name="book_id",columnDefinition = "INT NOT NULL")
    @ApiModelProperty(value = "book id",example = "1234567",position = 4)
    private int bookId;

    @Column(name="user_id",columnDefinition = "INT NOT NULL")
    @ApiModelProperty(value = "user id",example = "1234567",position = 5)
    private int userId;

    public Book2UserEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
