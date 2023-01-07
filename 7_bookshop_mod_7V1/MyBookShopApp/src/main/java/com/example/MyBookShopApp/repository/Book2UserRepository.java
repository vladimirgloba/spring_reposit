package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.Book2UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Book2UserRepository extends JpaRepository<Book2UserEntity, Integer> {


    @Query(value = "select * from book2user where book_id=?",nativeQuery = true)
    List<Book2UserEntity> getBook2UsersByIdBook(Integer id);

    @Query(value = "SELECT * from books join (select book_id, cast(count(type_id) as numeric)+sum( CASE when type_id=1  \n" +
            "                        then type_id*0.4 when type_id=2 then type_id*0.7 ELSE 0 END) as c  from book2user  group by  \n" +
            "                        book_id order by c) as foo on books.id=foo.book_id order by c",nativeQuery = true)
    Page<Book> popularBookListSorted(Pageable nextPage);

    @Query(value = "select foo.book_id as book_id,\n" +
            "sum((foo.b+foo.b*(case\n" +
            "    when foo.a=1 then 0.4\n" +
            "              when foo.a=2 then 0.7\n" +
            "              else 0\n" +
            "              end\n" +
            "))) as c from \n" +
            "(select book_id, type_id as a,count(type_id) as b from book2user group by type_id, book_id)\n" +
            "as foo group by foo.book_id order by c,foo.book_id limit ? offset ?",nativeQuery = true)
    List<Object[]> popularBooksId(Integer limit, Integer offset);

    @Query(value = "select count(foo1.book_id) from  (select foo.book_id as book_id,\n" +
            "sum((foo.b+foo.b*(case\n" +
            "    when foo.a=1 then 0.4\n" +
            "              when foo.a=2 then 0.7\n" +
            "              else 0\n" +
            "              end\n" +
            "))) as c from \n" +
            "(select book_id, type_id as a,count(type_id) as b from book2user group by type_id, book_id)\n" +
            "as foo group by foo.book_id order by c) as foo1",nativeQuery = true)
    Integer book2UserCount();

}
