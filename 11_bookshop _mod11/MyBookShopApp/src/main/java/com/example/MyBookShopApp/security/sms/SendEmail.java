package com.example.MyBookShopApp.security.sms;

import com.example.MyBookShopApp.data.SMS;
import com.example.MyBookShopApp.repository.SmsRepository;
import com.example.MyBookShopApp.security.*;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SendEmail {
    @Autowired
    BookstoreUserDetailsService bookstoreUserDetailsService;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    SmsRepository smsRepository;
    @Autowired
    JavaMailSender mailSender;
    private String contact;
    private String code;

    public SendEmail() {
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCode() {
        return code;
    }

    public void setCode() {
        this.code = generateSMSCOde();
    }
    private String generateSMSCOde(){
        String buffer="";
        while (buffer.length()!=6) {
            buffer=buffer+ (int)(Math.random() * 10);
        }
        return buffer;
    }
    public  void send(String contact){
        setCode();
        setContact(contact);
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setFrom("vladimir-globa@yandex.ru");
        simpleMail.setTo(contact);
        simpleMail.setSubject("http://localhost:8085/");
        simpleMail.setText("Код подтверждения: \n"+getCode());
        mailSender.send(simpleMail);
        SMS sms=new SMS();
        sms.setCode(getCode());
        sms.setId(Integer.valueOf((int) smsRepository.count()));
        sms.setExpireTime(LocalDateTime.now().plusMinutes(2));
        sms.setContact(contact);
        smsRepository.save(sms);
    }
    public void sendSaveChanges(SaveContactPayload payload){
        BookstoreUser user=new BookstoreUser();
        user.setName(payload.getName());
        user.setEmail(payload.getMail());
        user.setPassword(encoder.encode(payload.getPassword()));
        user.setPhone(payload.getPhone());
        user.setId(payload.getId());
        BookstoreUserDetails bookstoreUserDetails=new BookstoreUserDetails(user);
        String token=jwtUtil.generateToken(bookstoreUserDetails);
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setFrom("vladimir-globa@yandex.ru");
        simpleMail.setTo(payload.getMail());
        String name= payload.getName().replace(" ","_");
        String phone=payload.getPhone().replace(" ","_");
        simpleMail.setSubject("http://localhost:8085/");
        simpleMail.setText("Для подтверждения перейдите по ссылке: \n"+
                "http://localhost:8085/changedUser?name="+name+"&mail="+payload.getMail()
                +"&password="+payload.getPassword()+"&phone="+phone+"&token="+token+"&id="+
        payload.getId());
        System.out.println(simpleMail.getText());
        mailSender.send(simpleMail);
    }
}
