package com.example.MyBookShopApp.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@ApiModel(description = "data model of user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "user id",position = 1)
    private Integer id;


    @Column(name="hash",columnDefinition = "VARCHAR(255) NOT NULL")
    @ApiModelProperty(value = "user hash",position = 2)
    private String hash;

    @Column(name="reg_time",columnDefinition = " timestamp NOT NULL")
    @ApiModelProperty(value = "registration time",position = 3)
    private LocalDateTime regTime;

    @Column(name="balance")
    @ColumnDefault("0")
    @ApiModelProperty(value = "12345",position = 4)
    private Integer balance;

    @Column(name="name")
    @ApiModelProperty(value = "name",position = 5)
    private String name;

    public UserEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
