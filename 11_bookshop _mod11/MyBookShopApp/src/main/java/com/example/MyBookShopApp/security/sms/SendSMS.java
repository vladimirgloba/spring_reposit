package com.example.MyBookShopApp.security.sms;

import com.example.MyBookShopApp.data.SMS;
import com.example.MyBookShopApp.repository.SmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
@Service
public class SendSMS {
    @Autowired
    SmsRepository smsRepository;
    public String getSmsid() {
        return smsid;
    }

    public void setSmsid(String smsid) {
        this.smsid = smsid;
    }


    private String smsid;
private String smsCOde;

    public String getSmsCOde() {
        return smsCOde;
    }

    public void setSmsCOde() {

        this.smsCOde = generateSMSCOde();
    }

    private String smsBody;
private String numberPhone;
    public SendSMS() {
    }

    public String getSmsBody() {
        numberPhone=numberPhone.substring(1,numberPhone.length());
        numberPhone=numberPhone.replaceAll(" ","");
        numberPhone=numberPhone.replaceAll("\\(","");
        numberPhone=numberPhone.replaceAll("\\)","");
        numberPhone=numberPhone.replaceAll("-","");
        String buffer="https://sms.ru/sms/send?api_id=" +
                "CC5AD3A6-4A9B-1B12-9502-D2BDA19E4A09"+
                "&to=" +numberPhone+
                "&msg=" +"Ваш код подтверждения+=+\n"+
               getSmsCOde() +
                "&json=1&test=1";
        return buffer;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
    private String generateSMSCOde(){
        String buffer="";
        while (buffer.length()!=6) {
           buffer=buffer+ (int)(Math.random() * 10);
        }
        return buffer;
    }

    public void send(String numberPhone){
        setSmsCOde();
        setNumberPhone(numberPhone);
        System.out.println(getSmsBody());
        RestTemplate restTemplate = new RestTemplate();
        String stringPosts = restTemplate.getForObject(getSmsBody(), String.class);
        System.out.println(stringPosts);
        SMS sms=new SMS();
        sms.setCode(getSmsCOde());
        sms.setId(Integer.valueOf((int) smsRepository.count()));
        sms.setExpireTime(LocalDateTime.now().plusMinutes(2));
        sms.setContact(numberPhone);
        smsRepository.save(sms);
    }
}
