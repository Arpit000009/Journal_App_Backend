package com.pro.journalApp.controller;



import com.pro.journalApp.Entity.User;
import com.pro.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {



//    @GetMapping
//    public List<User> getAllUsers(){
//        return userService.getAll();
//    }

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());

//        userService.saveEntry(userInDb);
        userService.updateUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
