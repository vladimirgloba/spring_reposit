package com.example.MyBookShopApp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    // Jackson JSON serializer instance
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED; // 401
        response.setStatus(httpStatus.value());





        for(Cookie c:request.getCookies()){
          response.addCookie(c);
        }
String date= LocalDateTime.now().getDayOfMonth()+"."+LocalDateTime.now().getMonth()+"."+LocalDateTime.now().getYear()+
        " : "+LocalDateTime.now().getHour()+"."+LocalDateTime.now().getMinute();
        String message=exception.getMessage().toString();
            response.addCookie(new Cookie("TIMESTAMP", date));
            response.addCookie(new Cookie("CODE",String.valueOf( httpStatus.value())));
            response.addCookie(new Cookie("STATUS", httpStatus.name()));
            response.addCookie(new Cookie("TxtMESSAGE", message));


//        System.out.println(response.toString()+" = response.toString()"+"+++++++++++++++++++++++++++++++++++++++++++");

        response.sendRedirect("/signin");
    }
}
