package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

@Autowired
    private BookstoreUserRepository bookstoreUserRepository;


    public BookstoreUserDetailsService() {

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        BookstoreUser bookstoreUser=new BookstoreUser();
         bookstoreUser = bookstoreUserRepository.findBookstoreUserByEmail(s);

        if (bookstoreUser == null){
            bookstoreUser=bookstoreUserRepository.findBookstoreUserByPhone(s);

        }
        if(bookstoreUser!=null) {
            return new BookstoreUserDetails(bookstoreUser);
        }
        else{
            throw new UsernameNotFoundException("user not found doh!");
        }
    }
}
