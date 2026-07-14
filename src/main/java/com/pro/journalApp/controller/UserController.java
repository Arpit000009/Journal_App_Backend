package com.pro.journalApp.controller;



import com.pro.journalApp.Entity.User;
import com.pro.journalApp.api.response.WeatherResponse;
import com.pro.journalApp.repository.UserRepository;
import com.pro.journalApp.services.UserService;
import com.pro.journalApp.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {



//    @GetMapping
//    public List<User> getAllUsers(){
//        return userService.getAll();
//    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());

//        userService.saveEntry(userInDb);
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings(
            @RequestParam(defaultValue = "Bangalore") String city) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse =
                weatherService.getWeather(city);

        String greeting = "Hi " + authentication.getName();

        if (weatherResponse != null) {

            greeting +=
                    "\nToday's weather in "
                            + weatherResponse.getLocation().getName()
                            + " is "
                            + weatherResponse.getCurrent().getWeatherDescriptions().get(0)
                            + ".\nTemperature: "
                            + weatherResponse.getCurrent().getTemperature()
                            + "°C"
                            + " (Feels like "
                            + weatherResponse.getCurrent().getFeelslike()
                            + "°C)";
        }

        return ResponseEntity.ok(greeting);
    }
}
