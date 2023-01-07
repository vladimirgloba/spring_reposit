package com.example.MyBookShopApp.data;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class GeneratorBirthday {
    private Date birthday;
    private Random random;

    public GeneratorBirthday() {
        random=new Random();
    }

    public Date getBirthday() {
        int min=(int)LocalDate.now().minusYears(80).toEpochDay();
        int max=(int)LocalDate.now().minusYears(18).toEpochDay();
        long randomDay = min + random.nextInt(max - min);
        return Date.valueOf(LocalDate.ofEpochDay(randomDay));

    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;

    }
}
