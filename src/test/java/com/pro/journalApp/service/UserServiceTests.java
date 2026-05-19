package com.pro.journalApp.service;

import com.pro.journalApp.Entity.User;
import com.pro.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void testFindByUserName(){
        assertEquals(4,2+2);
        assertNotNull(userRepository.findByUserName("Ram"));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "Ram",
            "Arpit",
            "Manoj"
    })
    public void testtwo(String name){
        assertNotNull(userRepository.findByUserName(name),"failed for "+name);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a,int b,int expected){
        assertEquals(expected, a+b);
    }
}
