package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.Repository.AuthorRepository;
import com.example.MyBookShopApp.Repository.Book2UserRepository;
import com.example.MyBookShopApp.Repository.BookRepository;
import com.example.MyBookShopApp.Repository.EntityManagerRepository;
import com.example.MyBookShopApp.Service.Book2UserService;
import com.example.MyBookShopApp.Service.DataGenerator;
import com.example.MyBookShopApp.Service.GenreService;
import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.GenreIndexDto;
import com.example.MyBookShopApp.data.ParentGenre;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
//@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {
    //@Autowired
//    TestEntityCrudRepository testEntityCrudRepository;
//
    @Autowired
    BookRepository bookRepository;
@Autowired
    AuthorRepository authorRepository;

  @Autowired
  EntityManagerRepository em;
    @Autowired
    DataGenerator dataGenerator;
    @Autowired
    Book2UserService book2UserService;

    @Autowired
    GenreService genreService;

    @Autowired
    EntityManagerRepository entityManagerRepository;
    @Autowired
    Book2UserRepository repository;

    //    public CommandLineRunnerImpl() {
//
//    }
    public List<Book> popularBooks() {
        List<Object[]> objects = repository.popularBooksId(6, 0);
        List<Book> books = new ArrayList<>();
        for (Object[] object : objects) {
            Book book = bookRepository.findById(Integer.valueOf(object[0].toString())).get();
            books.add(book);
        }
        for (Object[] object : objects) {
            System.out.println(object[0].toString() + " = " + object[1].toString());
        }
        return books;
    }
private void updateAuthor(){
        List<Author>authors=authorRepository.findAll();
    Faker faker=new Faker();
    for(Author author:authors){
        String description=faker.lorem().sentence(180)+faker.lorem().sentence(120);
        description=description.replaceAll(","," ");

        String path="";
        int value =author.getId()%3;
        if (value==1){
            path="/assets/img/content/main/card1.png";

        }
        if (value==2){
            path="/assets/img/content/main/card2.png";
        }
        if (value==0){
            path="/assets/img/content/main/card3.png";
        }
        em.updateDB(sqlStringForUpdate(path,description,author.getId()));
    }

}
    private  String sqlStringForUpdate(String path,String description, Integer id){
        return "update authors set photo = '"+path+
                "', description = '"+description+"' where id ="+id;
    }
    @Override
    public void run(String... args) throws Exception {
//        for (int i = 0; i < 5; i++) {
//            createTestEntity(new TestEntity());
//        }
//
//        TestEntity readTestEntity = readTestEntityById(3L);
//        if (readTestEntity != null){
//            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("read " +readTestEntity.toString());
//        }else {
//            throw new NullPointerException();
//        }
//
//        TestEntity updatedTestEntity = updateTestEntityById(5L);
//        if (updatedTestEntity != null){
//            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("update "+updatedTestEntity.toString());
//        }else {
//            throw new NullPointerException();
//        }
//
//        deleteTesEntityById(4L);
//
//        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(bookRepository.findBooksByAuthor_FirstName("Jelene").toString());
//        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(bookRepository.customFindAllBooks().toString());


        repository.saveAll(dataGenerator.generatorSqlString());
        dataGenerator.insertDataInTag();
        dataGenerator.insertDataInBook2Tag();
        dataGenerator.genreGenerator();
        dataGenerator.setActualData();
        updateAuthor();

//        System.out.println(bookRepository.selectBookByGenreName("Лёгкое чтение",1000,0).size());
    }

}
//book2UserService.getSortedBooksByPopular();



//    private void deleteTesEntityById(Long id) {
//        TestEntity testEntity = testEntityCrudRepository.findById(id).get();
//        testEntityCrudRepository.delete(testEntity);
//    }
//
//    private TestEntity updateTestEntityById(Long id) {
//       TestEntity testEntity = testEntityCrudRepository.findById(id).get();
//       testEntity.setData("NEW DATA");
//       testEntityCrudRepository.save(testEntity);
//       return testEntity;
//    }
//
//    private TestEntity readTestEntityById(Long id) {
//        return testEntityCrudRepository.findById(id).get();
//    }
//
//    private void createTestEntity(TestEntity entity) {
//        entity.setData(entity.getClass().getSimpleName()+entity.hashCode());
//        testEntityCrudRepository.save(entity);
//    }

