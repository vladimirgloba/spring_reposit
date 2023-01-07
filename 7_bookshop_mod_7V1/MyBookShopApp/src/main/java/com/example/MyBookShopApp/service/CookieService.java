package com.example.MyBookShopApp.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

@Service
public class CookieService {

    public boolean containsBookInCocci(HttpServletRequest request, String slug) {
        if (request.getCookies() == null) {
            return false;
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getValue().contains(slug)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsBookInCookieByNameCookie(HttpServletRequest request, String status, String slug) {
        String nameCookie = status + "Contents";
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals(nameCookie) && c.getValue().contains(slug)) {
                return true;
            }
        }
        return false;
    }

    public boolean addBookInCocci(HttpServletRequest request, HttpServletResponse response,
                                  String slug, String status) {
        if (!getNameCocci(request, status)&& !containsBookInCocci(request,slug)) {//если нет имени контейнера
            response.addCookie(createCocci(slug, status));

            return true;
        }
        if (!getNameCocci(request, status)&& containsBookInCocci(request,slug)) {//если нет имени контейнера
            response.addCookie(createCocci(slug, status));
            removeFromContainer(request,response,slug,status);
            return true;
        }
        if (!containsBookInCocci(request, slug)) {//если куки вообще не содержат книгу
            for (Cookie c : request.getCookies()) {
                if (c.getName().contains(status) && !c.getValue().contains(slug)) {
                    StringJoiner stringJoiner = new StringJoiner("/");
                    stringJoiner.add(c.getValue()).add(slug);
                    Cookie cookie = new Cookie(status + "Contents", stringJoiner.toString());
                    cookie.setPath("/books");
                    response.addCookie(cookie);
                    return true;
                }
            }
        }
        if (!containsBookInCookieByNameCookie(request, status, slug)) {//если книга не в своём контейнере
            for (Cookie c : request.getCookies()) {
                if (c.getName().contains(status)) {//запись в свой контейнер
                    if(c.getValue().length()==0){
                        Cookie cookie = new Cookie(status + "Contents", slug);
                        cookie.setPath("/books");
                        response.addCookie(cookie);
                    }else{
                        Cookie cookie = new Cookie(status + "Contents", c.getValue()+"/"+slug);
                        cookie.setPath("/books");
                        response.addCookie(cookie);
                    }
                }
                else {//удаление не из своего контейнера
                    removeFromContainer(request,response,slug,status);
                }
            }
            return true;
        }
        return false;
    }

    private void removeFromContainer(HttpServletRequest request,HttpServletResponse response,
                                     String slug, String status){
        for (Cookie c:request.getCookies()){
            if(!c.getName().contains(status)){
                ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(c.getValue().split("/")));
                cookieBooks.remove(slug);
                Cookie cookie = new Cookie(c.getName(), String.join("/", cookieBooks));
                cookie.setPath("/books");
                response.addCookie(cookie);
            }
        }

    }
//    ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
//            cookieBooks.remove(slug);
//    Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
//            cookie.setPath("/books");
//            response.addCookie(cookie);


    private boolean getNameCocci(HttpServletRequest request, String status) {
        if (request.getCookies() == null) {
            return false;
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().contains(status)) {
                return true;
            }
        }
        return false;
    }

    private Cookie createCocci(String slug, String status) {
        String name = status + "Contents";
        Cookie cookie = new Cookie(name, slug);
        cookie.setPath("/books");
        return cookie;
    }

    public String getKeptOrCart(HttpServletRequest request, String slug) {
        String buffer = "";
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return buffer;
        }
        for (Cookie c : cookies) {
            if (c.getValue().contains(slug)) {
                buffer = c.getName();
            }
        }
        return buffer;
    }
    public Integer countBookInCookie(HttpServletResponse response,HttpServletRequest request,String status){
        Integer result=0;
        try{
        for(Cookie c:request.getCookies()){
            if(c.getName().contains(status)){
                ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(c.getValue().split("/")));
               cookieBooks.remove("");
                result = cookieBooks.size();
                for(String str:cookieBooks){
                    return result;
                }
            }
            else{
            }
        }
    }
        catch (Exception e){result=0;}
        return result;
    }
    public String[] cookieSlugsForListBook(HttpServletRequest request,String status){
Cookie[]cookies=request.getCookies();
for(Cookie c:cookies){
    if(c.getName().contains(status)){
        String[]buffer=c.getValue().split("/");
        return buffer;
    }
}return null;
    }
}


//        Cookie cookie = new Cookie("User", "Tom");
//        cookie.setPath("/books");
//        response.addCookie(cookie);
//       Cookie cookie = new Cookie("ANyDay", "Any day");
//        response.addCookie(cookie);
//        Cookie[] cookies = request.getCookies();
//        if (cartContents == null || cartContents.equals("")) {
//            Cookie cookie = new Cookie("cartContents", slug);
//            cookie.setPath("/books");
//            response.addCookie(cookie);
//            model.addAttribute("isCartEmpty", false);
//        } else if (!cartContents.contains(slug)) {
//            StringJoiner stringJoiner = new StringJoiner("/");
//            stringJoiner.add(cartContents).add(slug);
//            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
//            Cookie cookie1=new Cookie("myString","anyValue");
//            cookie.setPath("/books");
//
//            response.addCookie(cookie);
//            response.addCookie(cookie1);
//            model.addAttribute("isCartEmpty", false);
//        }



