package com.example.MyBookShopApp.dataService;

import com.example.MyBookShopApp.dataModele.Author;
import com.example.MyBookShopApp.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepo;

    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors=authorRepo.findAll();
        return authors.stream().collect(Collectors.groupingBy((Author a) -> {return a.getLastName().substring(0,1);}));
    }

    public void addAllAuthors(List<Author>authors){
        authorRepo.saveAll(authors);
    }
    public List<Author>getAllAuthors(){
        return authorRepo.findAll();
    }

}
