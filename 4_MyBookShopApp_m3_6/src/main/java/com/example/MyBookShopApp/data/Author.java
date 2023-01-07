package com.example.MyBookShopApp.data;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@Scope("prototype")
public class Author {
    private Integer id;
    private String lastName;
    private String firstName;
    private Date birthday;

    public Author() {
    }

    public Author(Integer id, String lastName, String firstName, Date birthday) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
