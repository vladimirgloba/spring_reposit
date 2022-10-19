package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.jboss.logging.Param;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {
//    @Autowired
//    JdbcTemplate jdbcTemplate;

    private final Logger logger = Logger.getLogger(BookRepository.class);
    //private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs,int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author",book.getAuthor());
        parameterSource.addValue("title",book.getTitle());
        parameterSource.addValue("size",book.getSize());
        jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES(:author, :title, :size)",parameterSource);
        logger.info("store new book: " + book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id",bookIdToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE id = :id",parameterSource);
        logger.info("remove book completed");
        return true;
    }

    @Override
    public boolean selectBookByAuthor(String authorName) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author",authorName);
        List<Book> books=new ArrayList<>();
        books = jdbcTemplate.query("SELECT * FROM books where author = :author",parameterSource, (ResultSet rs,int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        if(books.size()==0){return false;}
        else return true;

    }



    @Override
    public boolean selectBookByTitle(String title) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title",title);
        List<Book> books=new ArrayList<>();
        books = jdbcTemplate.query("SELECT * FROM books where title = :title",parameterSource, (ResultSet rs,int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        if(books.size()==0){return false;}
        else return true;
    }

    @Override
    public boolean selectBookBySize(int size) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("size",size);
        List<Book> books=new ArrayList<>();
        books = jdbcTemplate.query("SELECT * FROM books where size = :size",parameterSource, (ResultSet rs,int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        if(books.size()==0){return false;}
        else return true;
    }

    @Override
    public boolean searchBookById(Integer id) {
        logger.info("in searchBookById");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id",id);
        List<Book> books=new ArrayList<>();
        books = jdbcTemplate.query("SELECT * FROM books WHERE id = :id",parameterSource, (ResultSet rs,int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        if(books.size()==0){
            logger.info("in data base false");

            return false;}
        else{
            logger.info("in data base true");

            return true;
        }
    }

    @Override
    public void removeByRegex(String regex) {
        regex=regex.trim();
        if(isDigit(regex)){
            Integer size=Integer.valueOf(regex);
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("size",size);
            jdbcTemplate.update("DELETE FROM books WHERE size = :size",parameterSource);
            logger.info("remove book by regex (size) completed");
        }else{
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("title",regex);
            jdbcTemplate.update("DELETE FROM books WHERE title = :title",parameterSource);
            parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("author",regex);
            jdbcTemplate.update("DELETE FROM books WHERE author = :author",parameterSource);
            logger.info("remove book by regex (author or title) completed");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void defaultInit(){
        logger.info("default INIT in book repo bean");
    }

    public void defaultDestroy(){
        logger.info("default DESTROY in book repo bean");
    }
    private boolean isDigit(String str) {
        try {
            logger.info("before int value = Integer.parseInt(str)");
            int value = Integer.parseInt(str);
            logger.info("after int value = Integer.parseInt(str)");
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
