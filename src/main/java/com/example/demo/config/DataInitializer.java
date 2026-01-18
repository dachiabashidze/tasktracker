package com.example.demo.config;

import com.example.demo.enums.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Only create if database is empty
            if (userRepository.count() == 0) {

                User admin = new User();
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole(Role.ADMIN);
                admin.setCreateDate(LocalDateTime.now());
                admin.setUpdateDate(LocalDateTime.now());
                userRepository.save(admin);

                User manager = new User();
                manager.setEmail("manager@example.com");
                manager.setPassword(passwordEncoder.encode("manager"));
                manager.setRole(Role.MANAGER);
                manager.setCreateDate(LocalDateTime.now());
                manager.setUpdateDate(LocalDateTime.now());
                userRepository.save(manager);

                User user = new User();
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("user"));
                user.setRole(Role.USER);
                user.setCreateDate(LocalDateTime.now());
                user.setUpdateDate(LocalDateTime.now());
                userRepository.save(user);

                System.out.println("Test users created!");
            }
        };
    }
}
