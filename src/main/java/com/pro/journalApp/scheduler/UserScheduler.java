package com.pro.journalApp.scheduler;

import com.pro.journalApp.Entity.JournalEntry;
import com.pro.journalApp.Entity.User;
import com.pro.journalApp.cache.AppCache;
import com.pro.journalApp.repository.UserRepository;
import com.pro.journalApp.repository.UserRepositoryImpl;
import com.pro.journalApp.services.EmailService;
import com.pro.journalApp.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class UserScheduler {

    @Autowired
    private EmailService emailSerivce;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 9 * * SUN")
    public void fetchUsersAndSendMial(){
        List<User> users = userRepository.getUsersForSA();
        for(User user:users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
            String entry = String.join(" ",filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailSerivce.sendEmail(user.getEmail(),"Sentiment for last 7 days",sentiment);
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
