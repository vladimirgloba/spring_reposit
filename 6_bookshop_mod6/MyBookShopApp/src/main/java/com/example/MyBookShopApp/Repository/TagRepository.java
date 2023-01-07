package com.example.MyBookShopApp.Repository;

import com.example.MyBookShopApp.data.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {

    @Query(value = "select tags.name, foo.c from tags join \n" +
            "(select tag_id, count(book_id) as c from book2tag group by tag_id order by c) as foo \n" +
            "on  tags.id = foo.tag_id order by tags.name" , nativeQuery = true)
    List<Object[]> getListMainTagDto();

    @Query(value = "select min(c), max(c), avg(c) from \n" +
            "(select tag_id, count(book_id) as c from book2tag group by tag_id order by c) as foo",nativeQuery = true)
    List<Object[]> getListMaxMin();
}
