package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BalanceEntity;
import com.example.MyBookShopApp.data.CartDto;
import com.example.MyBookShopApp.data.CartDtoService;
import com.example.MyBookShopApp.repository.BalanceEntityRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@Api(description = "replenishment data")
public class ReplenishmentController {

    @Autowired
    CartDtoService cartDtoService;

    @Autowired
    BalanceEntityRepository balanceEntityRepository;

    @PostMapping("/replenishment/{id}")
    public String replenishment(@PathVariable("id") Integer userId, Model model,
                                HttpServletRequest request,
                                @RequestParam(name = "sum",defaultValue = "") String sum)  {
        Integer val=0;
        try{
            val=Integer.parseInt(sum);
        }
        catch (Exception e){

        }if(val>0) {
            BalanceEntity balanceEntity = new BalanceEntity();
            Integer id = Integer.parseInt(String.valueOf(balanceEntityRepository.count()));
            id = id == null ? 0 : id;
            balanceEntity.setId(id + 1);
            balanceEntity.setValue(Integer.parseInt(sum));
            balanceEntity.setTime(LocalDateTime.now());
            balanceEntity.setUserId(userId);
            balanceEntity.setDescription("Пополнение счёта нв сумму " + sum);
            balanceEntityRepository.save(balanceEntity);
        }
        CartDto cartDto=new CartDto();
        cartDto=cartDtoService.getCartDto(request);
        model.addAttribute("regOk", true);
        model.addAttribute("cartDto", cartDto);

        return "profile";
    }
}
