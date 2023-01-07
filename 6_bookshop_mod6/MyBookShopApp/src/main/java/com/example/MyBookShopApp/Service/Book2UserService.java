package com.example.MyBookShopApp.Service;

import com.example.MyBookShopApp.Repository.AuthorRepository;
import com.example.MyBookShopApp.Repository.Book2UserRepository;
import com.example.MyBookShopApp.Repository.EntityManagerRepository;
import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class Book2UserService {
    @Autowired
    private Book2UserRepository book2UserRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private EntityManagerRepository emRepo;

    @Autowired
    private BookService bookService;

//    public List<Book> getSortedBooksByPopular() {
//        List<Object[]> objects = emRepo.nativeQuerySelect(
//                "SELECT books.id, books.pub_date, books.author_id, books.is_bestseller, books.slug," +
//                        " books.title, books.image, books.description, books.discount, books.price, foo.c " +
//                        "from books join (select book_id, cast(count(type_id) as numeric)+sum( CASE when type_id=1 " +
//                        "then type_id*0.4 when type_id=2 then type_id*0.7 ELSE 0 END) as c  from book2user  group by " +
//                        "book_id order by c) as foo on books.id=foo.book_id order by c");
//        List<Book> books = new ArrayList<>();
//        for (Object[] object : objects) {
//            Book book = new Book();
//            book.setId(Integer.valueOf(object[0].toString()));
//            try {
//                java.util.Date utilDate = new SimpleDateFormat("yyyy-dd-MM").parse(object[1].toString());
//                book.setPubDate(new java.sql.Date(utilDate.getTime()));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Author author = (authorRepository.findById(Integer.valueOf(object[2].toString()))).get();
//            book.setAuthor(author);
//            book.setIsBestseller(Integer.valueOf((object[3].toString())));
//            book.setSlug(object[4].toString());
//            book.setTitle(object[5].toString());
//            book.setImage(object[6].toString());
//            book.setDescription(object[7].toString());
//            Integer integer = Integer.valueOf((int) (Double.valueOf(Double.parseDouble(object[9].toString()) / Double
//                    .parseDouble(object[8].toString()))).doubleValue());
//            book.setPriceOld(integer);
//            book.setPrice(Double.parseDouble(object[9].toString()));
//            books.add(book);
//        }
//        return books;
//    }
//private Page<Book> convertListToPage(Pageable pageable){
//            List<Book> entities = getSortedBooksByPopular();
//            int lowerBound = pageable.getPageNumber() * pageable.getPageSize();
//            int upperBound = Math.min(lowerBound + pageable.getPageSize() - 1, entities.size());
//            List<Book> subList = entities.subList(lowerBound, upperBound);
//            return new PageImpl<Book>(subList, pageable, subList.size());
//}

    //convert List to page
    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return book2UserRepository.popularBookListSorted(nextPage);
    }
//    public Page<Object[]> getPageOfPopularBookId(Integer offset, Integer limit) {
//        Pageable nextPage = PageRequest.of(offset, limit);
//        return new PageImpl<>(book2UserRepository.popularBooksId(),
//                nextPage,
//                book2UserRepository.book2UserCount());
//    }

}


