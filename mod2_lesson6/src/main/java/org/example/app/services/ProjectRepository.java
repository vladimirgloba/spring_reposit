package org.example.app.services;

import org.example.web.dto.Book;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

     boolean selectBookByAuthor(String authorName);
     boolean selectBookByTitle(String title);
    boolean selectBookBySize(int size);
    boolean searchBookById(Integer id);
    void removeByRegex(String regex);
}
