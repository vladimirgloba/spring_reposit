package com.example.MyBookShopApp.security;



import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class MyBookstoreUserRegisterTests {

    @Autowired
    private BookstoreUserRegister bookstoreUserRegister;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookstoreUserRepository bookstoreUserRepository;

    @Autowired
    BookstoreUserDetailsService bookstoreUserDetailsService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;





    @Test
    public void customAuthenticationForEmailTest(){
        BookstoreUser user=new BookstoreUser();
        Integer id=(int) bookstoreUserRepository.count()+1;
        user.setId(id);
        user.setName("Any_name");
        user.setPhone("Any_phone");
        user.setEmail("any-email@com.ru");
        user.setPassword(passwordEncoder.encode("Any_password"));
        bookstoreUserRepository.save(user);
        ContactConfirmationPayload payload=new ContactConfirmationPayload();
        payload.setContact("any-email@com.ru");
        payload.setCode("Any_password");
        Authentication authentication=bookstoreUserRegister.authenticate(payload);
        Boolean actualResult =authentication.isAuthenticated();
        assertThat(actualResult).isTrue();
    }
    @Test
    public void customAuthenticationForPhoneTest(){
        ContactConfirmationPayload payload=new ContactConfirmationPayload();
        payload.setContact("Any_phone");
        payload.setCode("Any_password");
        Authentication authentication=bookstoreUserRegister.authenticate(payload);
        Boolean actualResult =authentication.isAuthenticated();
        assertThat(actualResult).isTrue();
    }

    @Test
    public void getCurrentUserFromSecurityContextTest(){

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("any-email@com.ru",
                        "Any_password"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        BookstoreUserDetails
         userDetails =
                (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean actualResult=userDetails.getUsername().equals("any-email@com.ru");
        assertThat(actualResult).isTrue();

    }

    @Test
    public void getCurrentUserNameFromTokenTest(){
        BookstoreUserDetails userDetails=new BookstoreUserDetails(bookstoreUserRepository.findBookstoreUserByEmail("any-email@com.ru"));
        String token=jwtUtil.generateToken(userDetails);
        assertThat(jwtUtil.extractUsername(token).equals("any-email@com.ru")).isTrue();
    }

    @Test
    public void getFakerUserNameFromTokenTest(){
        BookstoreUserDetails userDetails=new BookstoreUserDetails(bookstoreUserRepository.findBookstoreUserByEmail("any-email@com.ru"));
        String token=jwtUtil.generateToken(userDetails);
        assertThat(jwtUtil.extractUsername(token).equals("No_name")).isFalse();
    }

    @Test
    public void validateCurrentUserByToken(){
        BookstoreUser user=bookstoreUserRepository.findBookstoreUserByEmail("any-email@com.ru");
        BookstoreUserDetails userDetails=new BookstoreUserDetails(bookstoreUserRepository.findBookstoreUserByEmail("any-email@com.ru"));
        String token=jwtUtil.generateToken(userDetails);
        assertThat( jwtUtil.validateToken(token,userDetails)).isTrue();
        bookstoreUserRepository.delete(user);
    }

    @Test
    public void customAuthenticationForFakerUserTest(){
        ContactConfirmationPayload payload=new ContactConfirmationPayload();
        payload.setContact("No_phone");
        payload.setCode("Any_password");
        try {
            Authentication authentication = bookstoreUserRegister.authenticate(payload);
            Boolean actualResult =authentication.isAuthenticated();
            assertThat(actualResult).isTrue();
        }catch (Exception e){
            Boolean actualResult =false;
            assertThat(actualResult).isFalse();
        }

    }


}
