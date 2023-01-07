package com.example.MyBookShopApp.Repository;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

    @Query(value = "select genre.name, foo.c from genre join \n" +
            "(select genre_id, count(book_id) as c from book2genre group by genre_id order by c) as foo \n" +
            "on  genre.id = foo.genre_id order by genre.name", nativeQuery = true)
    List<Object[]> getListMainPageDto();

    @Query(value = "select min(c), max(c), avg(c) from \n" +
            "(select genre_id, count(book_id) as c from book2genre group by genre_id order by c) as foo", nativeQuery = true)
    List<Object[]> getListMaxMin();

    @Query(value = "select id from genre where id in\n" +
            "(select parent_id from genre where id!=parent_id and parent_id not in\n" +
            " (select id from genre where id=parent_id));", nativeQuery = true)
    List<Integer> idParentThirdLevel();

    @Query(value = "select id from genre where id in(select id from genre where id=parent_id);", nativeQuery = true)
    List<Integer> idParentFirstLevel();

    @Query(value = "select count(*) from book2genre where genre_id in\n" +
            "(select id from genre where id in(select id from genre \n" +
            "where parent_id=(select id  from genre where name=?)))",nativeQuery = true)
    Integer parentLevelCountBook(String genreName);



    @Query(value = "select g.name, foo.books_count from genre as g join(\n" +
            "            select genre_id, count(book_id) as books_count from book2genre where genre_id in\n" +
            "            (select id from genre where parent_id = (select id as i from genre where name = ?))\n" +
            "            group by genre_id) as foo on g.id=foo.genre_id;", nativeQuery = true)
    List<Object[]> listGenreAndCountBooks(String parentName);

    @Query(value = " select foo1.parent_name, count (*) from book2genre join\n" +
            "(select genre.id as gd, parent_name from  genre join\n" +
            "(select id, name as parent_name from genre where parent_id =\n" +
            " (select id from genre where name = ?)\n" +
            "and id!=parent_id and id in (select parent_id from genre))as foo on genre.parent_id=foo.id) as foo1\n" +
            "on book2genre.genre_id=foo1.gd group by foo1.parent_name;",nativeQuery = true)
    List<Object[]> listParentSecondLevel(String parentName);

    @Query(value = "select name from genre where id=(select parent_id from genre where name = ?);",nativeQuery = true)
    String  getParentNameByGenre(String genreName);

    @Query(value = "select id from genre where name=? and id in (select parent_id from genre)",nativeQuery = true)
    Integer selectIdIsParent(String genreName);
}
