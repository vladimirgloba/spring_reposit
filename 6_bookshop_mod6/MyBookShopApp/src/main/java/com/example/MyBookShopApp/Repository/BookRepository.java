package com.example.MyBookShopApp.Repository;

import com.example.MyBookShopApp.data.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findBooksByAuthor_FirstName(String name);

    @Query("from Book")
    List<Book> customFindAllBooks();

    //NEW BOOK REST REPOSITORY COMMANDS

    List<Book> findBooksByAuthorFirstNameContaining(String authorFirstName);

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)", nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

@Query(value = "select * from books where is_bestseller=1",nativeQuery = true)
Page<Book> bestsellersList(Pageable nextPage);

    @Query(value = "select * from books order by pub_date, title desc" , nativeQuery = true)
    Page<Book> newBooks(Pageable nextPage);

    @Query(value = "select * from books join (select book_id from book2tag where tag_id=(\n" +
            "select id from tags where name=?))as foo on books.id=foo.book_id limit ? offset ?",nativeQuery = true)
    List<Book> getBooksByTagName(String tagName,Integer limit,Integer offset);


    @Query(value = "select * from books join \n" +
            "(select book_id from book2genre where genre_id in \n" +
            "(select id from ((select id from genre where  parent_id=(select id from genre where name = ?1)) union \n" +
            "(Select id from genre where name=?1)union \n" +
            "select id from genre where parent_id in(select parent_id from genre where Parent_id in \n" +
            "(select id from genre where parent_id=(select id from genre where name = ?1)) \n" +
            "and parent_id!=(select id from genre where name = ?1) group by parent_id))as foo) group by book_id) as buff \n" +
            "on books.id=buff.book_id order by title limit ?2 offset ?3", nativeQuery = true)
    List<Book> selectBookByGenreName(String nameGenre, Integer limit, Integer offset);

    @Query(value = "select count (*) from (select book_id from book2tag where tag_id=(\n" +
            "select id from tags where name=?))as foo",nativeQuery = true)
    Integer countBooksByTag(String nameTag);

    @Query(value = "select * from books  where author_id=? limit ? offset ?;", nativeQuery = true)
    List<Book> finedBooksByAuthorId(Integer authorId, Integer limit, Integer offset);

    @Query(value = "select * from books where pub_date>= ?1 and pub_date <= ?2 order by pub_date, title desc limit ?3 offset ?4",nativeQuery = true)
   List<Book> sortedListByDate(Date before, Date after, Integer limit, Integer offset);
}
