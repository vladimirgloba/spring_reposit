package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.repository.*;
import com.example.MyBookShopApp.service.Book2UserService;
import com.example.MyBookShopApp.service.DataGenerator;
import com.example.MyBookShopApp.service.GenreService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

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
    UserRepository userRepository;
    @Autowired
    EntityManagerRepository em;
    @Autowired
    DataGenerator dataGenerator;
    @Autowired
    Book2UserService book2UserService;

    @Autowired
    GenreService genreService;


    @Autowired
    Book2UserRepository repository;

    //    public CommandLineRunnerImpl() {
//
//    }

    private void updateAuthor() {
        List<Author> authors = authorRepository.findAll();
        Faker faker = new Faker();
        for (Author author : authors) {
            String description = faker.lorem().sentence(180) + faker.lorem().sentence(120);
            description = description.replaceAll(",", " ");

            String path = "";
            int value = author.getId() % 3;
            if (value == 1) {
                path = "/assets/img/content/main/card1.png";

            }
            if (value == 2) {
                path = "/assets/img/content/main/card2.png";
            }
            if (value == 0) {
                path = "/assets/img/content/main/card3.png";
            }
            em.updateDB(sqlStringForUpdate(path, description, author.getId()));
        }

    }

    private String sqlStringForUpdate(String path, String description, Integer id) {
        return "update authors set photo = '" + path +
                "', description = '" + description + "' where id =" + id;
    }

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

    @Override
    public void run(String... args) throws Exception {
        System.out.println("please wait I'm making 50 entries in USERS");
        userRepository.saveAll(dataGenerator.generateUsers());
        System.out.println("please wait I'm making 10_000 entries in Book2User");
        repository.saveAll(dataGenerator.generatorBook2User());
        System.out.println("please wait I'm making 52 entries in TAGS");
        dataGenerator.insertDataInTag();
        System.out.println("please wait I'm making 10_000 entries in Book2Tag");
        dataGenerator.insertDataInBook2Tag();
        System.out.println("please wait I'm making 59 entries in GENRE & 10_000 in book2genre");
        dataGenerator.genreGenerator();
        System.out.println("please wait I'm making Actual Data in BOOKS");
        dataGenerator.setActualData();
        System.out.println("please wait I'm making Actual photo in authors");
        updateAuthor();
        System.out.println("please wait I'm making insert In BookFile");
        dataGenerator.insertInBookFile();
        System.out.println("please wait I'm making insert In review & like" +
                "Для каждой книги с чётным id создаются записи с комментариями,\\n\" +\n" +
                "                \"комментарий содержит от 100 до 250-ти рандомных словоформ, лайки - значение 1||-1 - тип int");
        dataGenerator.reviewAndLike();
        System.out.println(",\n" +
                "Для рейтинга создана таблица с полями user_id & book_id,- рейтинги у книг\n" +
                "по нечётным id, пара пользователь-книга  уникальны, если такая пара уже существует,\n" +
                " то  перезаписывается значение, если пары нет - то создаётся. Значение stars от 1 \n" +
                "до 5 и 0=false -по умолчанию");
        dataGenerator.forRating();
        System.out.println("end load in DB");


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

