package com.example.MyBookShopApp.security;


import com.example.MyBookShopApp.aop.ForAllMethodsAspect;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class BookstoreUserRegister {

    private final BookstoreUserRepository bookstoreUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;

    @Autowired
    public BookstoreUserRegister(BookstoreUserRepository bookstoreUserRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil) {
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public BookstoreUser registerNewUser(RegistrationForm registrationForm) {

        if (bookstoreUserRepository.findBookstoreUserByEmail(registrationForm.getEmail()) == null) {
            BookstoreUser user = new BookstoreUser();
            user.setName(registrationForm.getName());
            user.setEmail(registrationForm.getEmail());
            user.setPhone(registrationForm.getPhone());
            user.setId((int)bookstoreUserRepository.count()+1);
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));
            bookstoreUserRepository.save(user);
            return user;
        }
        return null;
    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        if(!isMail(payload.getContact())){
            BookstoreUser user=bookstoreUserRepository.findBookstoreUserByPhone(payload.getContact());
            if(user!=null){
                payload.setContact(user.getEmail());
            }
        }
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }
@ForAllMethodsAspect
    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        Authentication authentication=authenticate(payload);
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }
    @ForAllMethodsAspect
    public Authentication authenticate(ContactConfirmationPayload payload) throws AuthenticationException {
        String contact = payload.getContact();
        String password =payload.getCode().replace(" ","");
        BookstoreUser bookstoreUser=new BookstoreUser();
        bookstoreUser=bookstoreUserRepository.findBookstoreUserByEmail(contact);

        if(bookstoreUser==null){
            bookstoreUser=bookstoreUserRepository.findBookstoreUserByPhone(contact);

        }
        if (bookstoreUser == null) {
            throw new BadCredentialsException("1000");
        }

        if (!passwordEncoder.matches(password,bookstoreUser.getPassword())){
            throw new BadCredentialsException("1000");
        }
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(contact);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public Object getCurrentUser() {
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getBookstoreUser();
    }
    private boolean isMail(String str){
        String regex="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return Pattern.matches(regex,str);
    }
}
