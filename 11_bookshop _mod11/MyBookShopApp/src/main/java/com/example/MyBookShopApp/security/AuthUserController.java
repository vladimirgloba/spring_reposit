package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.aop.AboutRequestInformation;
import com.example.MyBookShopApp.data.CartDto;
import com.example.MyBookShopApp.data.CartDtoService;
import com.example.MyBookShopApp.repository.BalanceEntityRepository;
import com.example.MyBookShopApp.repository.EntityManagerRepository;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import com.example.MyBookShopApp.security.sms.SendEmail;
import com.example.MyBookShopApp.security.sms.SendSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@Controller
public class AuthUserController {
    private final BookstoreUserRegister userRegister;

    @Autowired
    BalanceEntityRepository balanceEntityRepository;
    @Autowired
    private EntityManagerRepository em;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private SendSMS sendSMS;

    @Autowired
    private BookstoreUserRepository bookstoreUserRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CartDtoService cartDtoService;

    private Boolean regOk = false;

    @Autowired
    public AuthUserController(BookstoreUserRegister userRegister) {
        this.userRegister = userRegister;
    }

    @GetMapping("/signin")
    public String handleSignIn(HttpServletRequest servletRequest, Model model) {
        model.addAttribute("regOk", regOk);
        regOk = false;
        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(HttpServletRequest servletRequest, Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(HttpServletRequest servletRequest, HttpServletResponse httpServletResponse, @RequestBody ContactConfirmationPayload payload) {

        if (!isMail(payload.getContact())) {
            sendSMS.send(payload.getContact());
        } else {
            sendEmail.send(payload.getContact());
        }
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(HttpServletRequest servletRequest, @RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        if (userRegister.inspectCode(payload)) {
            response.setResult("true");
        }
        return response;
    }

    @PostMapping("/requestEmailfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestEmailConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        sendEmail.send(payload.getContact());
        response.setResult("true");
        return response;
    }

    @PostMapping("/reg")
    public String handleUserregistration(HttpServletRequest servletRequest, RegistrationForm registrationForm, Model model) {
        regOk = userRegister.registerNewUser(registrationForm) != null;
        return "redirect:/signin";
    }

    @AboutRequestInformation
    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(HttpServletRequest request, @RequestBody ContactConfirmationPayload payload, HttpServletResponse httpServletResponse) {
        ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        httpServletResponse.addCookie(cookie);
        return loginResponse;
    }

    @GetMapping("/my")
    public String handleMy(HttpServletRequest request, Model model) {
        String token = "";
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("token")) {
                token = c.getValue();
            }
        }
        String name = jwtUtil.extractUsername(token);

        BookstoreUser user = new BookstoreUser();
        user = bookstoreUserRepository.findBookstoreUserByEmail(name);
        CartDto cartDto=new CartDto();
        cartDto=cartDtoService.getCartDto(request);
        model.addAttribute("cartDto", cartDto);
        regOk = true;
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model, HttpServletRequest request) {
        CartDto cartDto = new CartDto();
        cartDto = cartDtoService.getCartDto(request);
        model.addAttribute("regOk", true);
        model.addAttribute("cartDto", cartDto);
        return "profile";
    }

    @GetMapping("/changedUser")

    public String changeProfile(Model model, HttpServletResponse response, HttpServletRequest request,
                                @RequestParam(name = "id", required = false) Integer id,
                                @RequestParam(name = "name", required = false) String name,
                                @RequestParam(name = "phone", required = false) String phone,
                                @RequestParam(name = "mail", required = false) String mail,
                                @RequestParam(name = "password", required = false) String password,
                                @RequestParam(name = "token", required = false) String token) {
        BookstoreUser user = new BookstoreUser();
        user.setId(id);
        user.setPhone("+" + phone.replace("_", " "));
        user.setPassword(encoder.encode(password));
        user.setName(name.replace("_", " "));
        user.setEmail(mail);
        String sql = "update users set email='" + user.getEmail() + "', name ='" + user.getName() + "', password='" +
                user.getPassword() + "', phone='" + user.getPhone() + "' where id = " + user.getId();
        em.updateBD(sql);
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);
        ContactConfirmationResponse contactConfirmationResponse = new ContactConfirmationResponse();
        contactConfirmationResponse.setResult(token);
        ContactConfirmationPayload contactConfirmationPayload = new ContactConfirmationPayload();
        contactConfirmationPayload.setContact(user.getEmail());
        contactConfirmationPayload.setCode(password);
        userRegister.login(contactConfirmationPayload);
        CartDto cartDto = new CartDto();
        cartDto.setMany(balanceEntityRepository.selectManyByUserId(user.getId()));
        cartDto.setUser(user);
        cartDto.setTransactionList(cartDtoService.transactionDtoList(user.getId()));

        model.addAttribute("regOk", true);
        model.addAttribute("cartDto", cartDto);

        regOk = true;
        return "my";
    }

    @RequestMapping(value = "/saveChange", method = RequestMethod.POST)
    public String saveProfile(SaveContactPayload payload, Model model,
                              @RequestParam(name = "id", required = false) Integer id, HttpServletRequest request, HttpServletResponse response) {
        payload.setId(id);
        try {
            if (payload.getPasswordReply().length() > 5 && payload.getPassword().length() > 5
                    && payload.getPasswordReply().equals(payload.getPassword())) {
                model.addAttribute("visibilityBtn", true);
                CartDto cartDto = new CartDto();
                cartDto = cartDtoService.getCartDto(request);
                model.addAttribute("regOk", true);
                model.addAttribute("cartDto", cartDto);
                sendEmail.sendSaveChanges(payload);
                Cookie[] cookies = request.getCookies();
                if (cookies != null)
                    for (Cookie cookie : cookies) {
                        cookie.setValue("");
                        cookie.setPath("/");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                return "profile";
            } else {
                model.addAttribute("visibilityBtn", false);
                CartDto cartDto = new CartDto();
                cartDto = cartDtoService.getCartDto(request);
                model.addAttribute("regOk", true);
                model.addAttribute("cartDto", cartDto);
                return "profile";
            }
        } catch (Exception e) {
            regOk = false;
            return "profile";
        }
    }

    private boolean isMail(String str) {
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return Pattern.matches(regex, str);
    }

}
