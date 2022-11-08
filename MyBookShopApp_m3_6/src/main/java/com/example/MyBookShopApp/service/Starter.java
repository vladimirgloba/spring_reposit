package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
@Service
public class Starter implements CommandLineRunner {
    @Autowired
    DataGenerator dataGenerator;

    @Override
    public void run(String... args) throws Exception {
         System.out.println(dataGenerator.addDataInTableAuthor());
        System.out.println();
    }
}
