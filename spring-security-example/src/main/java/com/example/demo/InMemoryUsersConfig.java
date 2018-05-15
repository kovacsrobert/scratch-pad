package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryUsersConfig {

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        // ensure the passwords are encoded properly
        UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password")
                .authorities(UserRoles.ROLE_USER)
                //.roles(UserRoles.USER)
                .build());
        manager.createUser(users.username("admin").password("password")
                .authorities(UserRoles.ROLE_USER, UserRoles.ROLE_ADMIN)
                //.roles(UserRoles.USER, UserRoles.ADMIN)
                .build());
        return manager;
    }
}
