package com.example.MyBookShopApp.data;

import com.github.javafaker.Artist;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Service
public class DataGenerator {
    @Autowired
    private BookService bookService;

    @Autowired
    GeneratorBirthday generatorBirthday;

    @Autowired
    private JdbcTemplate jdbcTemplate;



    public DataGenerator() {
    }

    private List<Integer> listIdAuthor(){
        List<Integer> integerList = jdbcTemplate.query("SELECT AUTHOR_ID FROM books", (ResultSet rs, int rownum)->{

            return rs.getInt("AUTHOR_ID");
        });
        List<Integer>integerList1=new ArrayList<>();
        for(Integer integer:integerList){
            if(!integerList1.contains(integer)){
                integerList1.add(integer);
            }
        }
        return integerList1 ;
    }

    private void createTableAuthor(){
        jdbcTemplate.update("DROP TABLE IF EXISTS authors;\n" +
                "\n" +
                "CREATE TABLE authors(\n" +
                "id INT Unique,\n" +
                "first_name  VARCHAR(250) NOT NULL,\n" +
                "last_name  VARCHAR(250) NOT NULL,\n" +
                "birthday DATE not null"+
                ");");
    }
    private List<Author> getAuthors(){
        List<Author>authorList=new ArrayList<>();
        Faker faker = new Faker();
        final Name name = faker.name();

        for(Integer integer:listIdAuthor()){
            Author author=new Author(
                    integer,
                    name.firstName().replace("'",""),
                    name.lastName().replace("'",""),
                    generatorBirthday.getBirthday()
            );
            authorList.add(author);
        }
        return authorList;
    }

    public String addDataInTableAuthor(){
        createTableAuthor();
        String sql1Part="insert into authors (id, first_name, last_name, birthday) values\n";
        String sql2Part="";
        for (Author author:getAuthors()){
            sql2Part=sql2Part+"("+author.getId()+", '"+author.getFirstName()+ "', '"+author.getLastName()+"', '"+author.getBirthday()+"'),\n";
        }
        sql2Part=sql2Part.substring(0,sql2Part.length()-2);
        sql1Part=sql1Part+sql2Part+";";
        jdbcTemplate.update(sql1Part);
        return sql1Part;
    }


}
