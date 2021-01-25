package com.sigsauer.exchangerates.configuration;

import com.sigsauer.exchangerates.entity.User;
import com.sigsauer.exchangerates.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DatabaseConfiguration {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> log.info("Database initialization..."+
                userRepository.save(new User("test", "test", "_test")));
    }
}
