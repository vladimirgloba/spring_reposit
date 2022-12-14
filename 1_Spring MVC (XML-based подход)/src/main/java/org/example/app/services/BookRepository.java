package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public void removeAllByRegex(String inputString) {
        int flag=0;
        if(isNumeric(inputString)){

            Integer size=Integer.valueOf(inputString);
            System.out.println(size+"===========");
            for(Book book:retreiveAll()){
                if(Objects.equals(book.getSize(), size)){
                    repo.remove(book);
                    logger.info("matches by size - remove book completed: " + book);
                    flag++;
                }
            }
        }
        else{
            for(Book book:retreiveAll()){
                if(book.getAuthor().equals(inputString)||book.getTitle().equals(inputString)){
                    repo.remove(book);
                    logger.info("matches by author or tittle  - remove book completed: " + book);
                    flag++;
                }
            }

        }
        if(flag==0){
            logger.info("no matches - no remove book  ");
        }

    }

    public boolean isNumeric(String string) {
        Integer intValue;

        System.out.println(String.format("Parsing string: \"%s\"", string));

        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }
}
