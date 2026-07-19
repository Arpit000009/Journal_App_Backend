package com.pro.journalApp.service;

import com.pro.journalApp.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;


    @Test
    void testSandMail(){
        emailService.sendEmail("arpitpandey241@gmail.com",
                "Testing java mail sender",
                "hi, aap akise hain?");
    }
}
