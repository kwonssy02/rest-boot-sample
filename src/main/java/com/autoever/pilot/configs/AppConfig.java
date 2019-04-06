package com.autoever.pilot.configs;

import com.autoever.pilot.model.User;
import com.autoever.pilot.users.Role;
import com.autoever.pilot.users.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Autowired
            UserService userService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                User keesun = User.builder()
                    .email("keesun@email.com")
                    .password("keesun")
                    .roles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.USER)))
                    .build();
                userService.saveAccount(keesun);
            }
        };
    }
}
