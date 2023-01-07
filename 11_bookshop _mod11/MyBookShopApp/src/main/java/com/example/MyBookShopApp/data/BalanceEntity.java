package com.example.MyBookShopApp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance_transaction")
@ApiModel(description = "data model of users transaction")
public class BalanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id of the type of binding of books to users",position = 1)
    private Integer id;

    @Column(name="user_id",columnDefinition = "INT NOT NULL")
    @ApiModelProperty(value = "user id",example = "1234567",position = 5)
    private int userId;
    private LocalDateTime time;

    @Column(name="book_id",columnDefinition = "INT NOT NULL")
    @ApiModelProperty(value = "book id",example = "1234567",position = 4)
    private int bookId;
    @Column(name="description",columnDefinition = "TEXT NOT NULL")
    private String description;
    private int value;

    public BalanceEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
//    balance_transaction — транзакции по счетам пользователей
//
//    id INT NOT NULL AUTO_INCREMENT
//    user_id INT NOT NULL — идентификатор пользователя
//    time DATETIME NOT NULL — дата и время транзакции
//    value INT NOT NULL  DEFAULT 0 — размер транзакции (положительный — зачисление, отрицательный — списание)
//    book_id INT NOT NULL — книга, за покупку которой произошло списание, или NULL
//    description TEXT NOT NULL — описание транзакции: если зачисление, то откуда, если списание, то на что

}
