package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.security.BookstoreUser;
import com.example.MyBookShopApp.security.BookstoreUserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UserRepositoryTest {

    @Autowired
    BookstoreUserRepository bookstoreUserRepository;

    @Test
void findBookstoreUserByEmail(){
        BookstoreUser user=new BookstoreUser();
//        Integer id=(int) bookstoreUserRepository.count()+1;
//        user.setId(id);
//        user.setName("Any_name");
//        user.setPhone("Any_phone");
//        user.setEmail("Any_email");
//        user.setPassword("Any_password");
//        bookstoreUserRepository.save(user);
        user=bookstoreUserRepository.findBookstoreUserByEmail("any-email@com.ru");
        Boolean actualResult =(user!=null)?true:false;
        assertThat(actualResult).isTrue();
    }

    @Test
    void findBookstoreUserByPhone(){
        BookstoreUser user=new BookstoreUser();
        findBookstoreUserByEmail();
        user=bookstoreUserRepository.findBookstoreUserByPhone("Any_phone");
        Boolean actualResult =(user!=null)?true:false;
        assertThat(actualResult).isTrue();
        bookstoreUserRepository.delete(user);
    }

}
