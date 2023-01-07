package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.SMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsRepository extends JpaRepository<SMS, Integer> {

    @Query(value = "select * from sms where contact=?1 and code=?2",nativeQuery = true)
    SMS getSMS(String contact, String Code);


}
