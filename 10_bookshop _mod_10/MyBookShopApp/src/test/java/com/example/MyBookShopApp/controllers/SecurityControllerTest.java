package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.security.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WebAppConfiguration
public class SecurityControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    @Autowired
    WebApplicationContext wac;
    @Autowired
    BookstoreUserDetailsService bookstoreUserDetailsService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookstoreUserRepository bookstoreUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BookstoreUserRegister userRegister;

    @Test
    public void testAuthenticatedAccessToSigninPage() throws Exception {
        BookstoreUser user = new BookstoreUser();
        user.setName("Any_name");
        user.setEmail("any-email@com.ru");
        user.setPhone("Any_phone");
        user.setId((int) bookstoreUserRepository.count() + 1);
        user.setPassword(passwordEncoder.encode("Any_password"));
        bookstoreUserRepository.save(user);
        mockMvc.perform(formLogin("/signin").user("any-email@com.ru").password("Any_password"))
                .andExpect(status().is(302))
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithUserDetails("any-email@com.ru")
    public void testProfilePageWithEmail() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(authenticated());
    }

    @Test
    @WithUserDetails("Any_phone")
    public void testProfilePageWithPhone() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(authenticated());
    }

    @Test
    @WithUserDetails("any-email@com.ru")
    public void accessOnlyAuthorizedPageFailTest() throws Exception {
        mockMvc.perform(get("/my"))
                .andExpect(authenticated())
                .andDo(print())
                .andExpect(xpath("/html/body/div/div/main/div/div[2]/div[1]/div[2]/div[1]")
                        .string("Кондратенко Александр Петрович"));


    }
    @Test
    public void testCreationTokenInLoginController() throws Exception {
        ContactConfirmationPayload payload = new ContactConfirmationPayload();
        payload.setContact("any-email@com.ru");
        payload.setCode("Any_password");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(payload);
        mockMvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("token"));
    }



}
