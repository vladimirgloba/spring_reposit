package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.repository.BalanceEntityRepository;
import com.example.MyBookShopApp.security.BookstoreUser;
import com.example.MyBookShopApp.security.BookstoreUserRepository;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CartDtoService {
    @Autowired
    BalanceEntityRepository balanceEntityRepository;
    @Autowired
    BookstoreUserRepository bookstoreUserRepository;
    @Autowired
    JWTUtil jwtUtil;
    private CartDto cartDto;

    public CartDtoService() {
    }

    public CartDto getCartDto(HttpServletRequest request) {
        this.cartDto = new CartDto();
        cartDto.setUser(user(request));
        cartDto.setBookList(new ArrayList<>());
        cartDto.setTransactionList(transactionDtoList(cartDto.getUser().getId()));
        double summ=0;
        try{
            summ=balanceEntityRepository.selectManyByUserId(cartDto.getUser().getId());
        }catch (Exception e){

        }
        cartDto.setMany(summ);
        return cartDto;
    }

    private String getToken(HttpServletRequest request) {
        String token = "";

        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("token")) {
                token = c.getValue();
            }
        }
        return token;
    }

    private BookstoreUser user(HttpServletRequest request) {
        String token = getToken(request);
        String contact = jwtUtil.extractUsername(token);
        BookstoreUser bookstoreUser = new BookstoreUser();
        bookstoreUser = bookstoreUserRepository.findBookstoreUserByEmail(contact);
        if (bookstoreUser == null) {
            bookstoreUser = bookstoreUserRepository.findBookstoreUserByPhone(contact);
        }
        return bookstoreUser;
    }
public List<TransactionDto> transactionDtoList(Integer userId){
        List<BalanceEntity >balanceEntities=balanceEntityRepository.selectBalanceByUserId(userId);
        List<TransactionDto>dtos=new ArrayList<>();
        for(BalanceEntity balanceEntity:balanceEntities){
            TransactionDto transactionDto=new TransactionDto();
            transactionDto.setDescription(balanceEntity.getDescription());
            transactionDto.setSumma(balanceEntity.getValue());
            String time="";
            time=String.valueOf(balanceEntity.getTime().getDayOfMonth());
            time=time+" "+balanceEntity.getTime().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
            time=time+" "+String.valueOf(balanceEntity.getTime().getYear());
            time=time+" "+balanceEntity.getTime().getHour();
            time=time+":"+balanceEntity.getTime().getMinute();
            transactionDto.setTime(time);
            dtos.add(transactionDto);
        }
        return dtos;
}
    public void setCartDto(CartDto cartDto) {
        this.cartDto = cartDto;
    }
}
