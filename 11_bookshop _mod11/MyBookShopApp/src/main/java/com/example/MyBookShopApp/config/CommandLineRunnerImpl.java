package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.security.BookstoreUser;
import com.example.MyBookShopApp.security.BookstoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {
@Autowired
    BookstoreUserRepository bookstoreUserRepository;
@Autowired
    PasswordEncoder encoder;
//    TestEntityCrudRepository testEntityCrudRepository;
//    BookRepository bookRepository;
//
//    @Autowired
//    public CommandLineRunnerImpl(TestEntityCrudRepository testEntityCrudRepository, BookRepository bookRepository) {
//        this.testEntityCrudRepository = testEntityCrudRepository;
//        this.bookRepository = bookRepository;
//    }

    @Override
    public void run(String... args) throws Exception {
        BookstoreUser user=new BookstoreUser();
        user.setPhone("+7 (921) 814-00-63");
        user.setId(1000);
        user.setPassword(encoder.encode("wwwwwwwwwwww"));
        user.setEmail("vladimir-globa@yandex.ru");
        user.setName("Глоба Владимир");
        bookstoreUserRepository.save(user);
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
    }

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
}
