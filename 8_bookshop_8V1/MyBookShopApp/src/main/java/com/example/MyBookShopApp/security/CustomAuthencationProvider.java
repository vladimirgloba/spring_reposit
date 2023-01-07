package com.example.MyBookShopApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthencationProvider implements AuthenticationProvider {
    @Autowired
    private BookstoreUserRepository dao;
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String userName = authentication.getName();

        BookstoreUser myUser = dao.findBookStoreUserByPhone(userName);
        //смотрим, найден ли пользователь в базе
        if (myUser == null) {
            throw new BadCredentialsException("Unknown user "+userName);
        }

                BookstoreUserDetails principal = new BookstoreUserDetails(myUser);

        return new UsernamePasswordAuthenticationToken(
                principal,  principal.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
