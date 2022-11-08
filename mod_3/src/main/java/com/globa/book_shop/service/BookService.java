package com.globa.book_shop.service;

import com.globa.book_shop.modele.Author;
import com.globa.book_shop.modele.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.*;

@Service
public class BookService {

    @Autowired
    GeneratorBirthday generator;
    @Autowired
    Book book;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;


    }

    private void createTableBook() {
        String sqlForCreateTableBook = "DROP TABLE IF EXISTS book;\n" +
                "\n" +
                "CREATE TABLE  book(\n" +
                "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "id_author int NOT NULL,\n" +
                "title VARCHAR(250) NOT NULL,\n" +
                "price_old  VARCHAR(250) DEFAULT NULL,\n" +
                "price  VARCHAR(250) DEFAULT NULL\n" +
                ");";
        jdbcTemplate.update(sqlForCreateTableBook);
    }

    public void createTableAuthor() {
        createTableBook();
        String sqlForCreateTableAuthor = "DROP TABLE IF EXISTS author;\n" +
                "\n" +
                "CREATE TABLE  author(\n" +
                "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "last_name VARCHAR(250) NOT NULL,\n" +
                "first_name VARCHAR(250) NOT NULL,\n" +
                "patronymic  VARCHAR(250) DEFAULT NULL,\n" +
                "birthday DATE DEFAULT NULL\n" +
                ");";
        jdbcTemplate.update(sqlForCreateTableAuthor);
        Map<Integer, String> authorsMap = new HashMap<>();
        Set<String> nameList = new HashSet<>();
        jdbcTemplate.query("select author from books", (ResultSet rs, int rownum) ->
                nameList.add(rs.getString(1)));

        String bufferQuery = "";
        for (String str : nameList) {
            List<String> myList = new ArrayList<String>(Arrays.asList(str.split(" ")));
            if (myList.size() == 2) {
                myList.add("");
            }
            bufferQuery = bufferQuery + "(" + "'" + myList.get(0) + "'" + ", " + "'" + myList.get(1) + "'" + ", " + "'" + myList.get(2) + "'" + "," + "'" + generator.getBirthday() + "'" + "),\n";
        }
        bufferQuery = "insert into author (last_name, first_name, patronymic, birthday) values \n" +
                bufferQuery.substring(0, (bufferQuery.length() - 2)) + ";" + " \n";
        System.out.println(bufferQuery);
        jdbcTemplate.update(bufferQuery);
        List<Author> authorList = jdbcTemplate.query("select * from author", (ResultSet rs, int rownum) ->
                {
                    Author author = new Author();
                    author.setId(rs.getInt("id"));
                    author.setLastName(rs.getString("last_name"));
                    author.setFirstName(rs.getString("first_name"));
                    author.setPatronymic(rs.getString("patronymic"));
                    author.setBirthday(rs.getDate("birthday"));
                    return author;
                }
        );
        for (Author author : authorList) {
            String name = (author.getLastName() + " " + author.getFirstName() + " " + author.getPatronymic()).trim();
            Integer id = author.getId();

            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("author", name);
            List<Book> listBook = jdbcTemplate.query("SELECT * FROM books where author = ?", new Object[]{name}, (ResultSet rs, int rowNum) ->

                    new Book(rs.getInt("id"), rs.getString("author"),
                            rs.getString("title"), rs.getString("priceOld"),
                            rs.getString("price"))
            );

            for (Book book : listBook) {
                String insertInBook = "insert into book (id_author, title, price_old,price) values" +
                        " (" + id + ", '" + book.getTitle() + "', '" + book.getPriceOld() + "', '" + book.getPrice() + "');";
                System.out.println(insertInBook);
                jdbcTemplate.update(insertInBook);

            }
        }

    }

    public List<Book> getBookList() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM book", (ResultSet rs, int rownum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            Integer id_author = rs.getInt("id_author");
            Author author = jdbcTemplate.queryForObject("select * from author where id=?",
                    new Object[]{id_author}, (ResultSet rs1, int rowNum) ->
                            new Author(rs1.getInt("id"),
                                    rs1.getString("last_name")
                                    , rs1.getString("first_name")
                                    , rs1.getString("patronymic")
                                    , rs1.getDate("birthday")
                            ));

            book.setAuthor((author.getFirstName() + " " + author.getLastName() + " " + author.getPatronymic()).trim());
            book.setTitle(rs.getString("title"));
            book.setPriceOld(rs.getString("price_old"));
            book.setPrice(rs.getString("price"));
            return book;
        });
        return books;
    }

    public List<Author> getAuthorList() {
        List<Author> authorList = jdbcTemplate.query("select * from author", (ResultSet rs, int rownum) -> {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setLastName(rs.getString("last_name"));
            author.setFirstName(rs.getString("first_name"));
            author.setPatronymic(rs.getString("patronymic"));
            author.setBirthday(rs.getDate("birthday"));
            return author;
        });
        return authorList;
    }

}
