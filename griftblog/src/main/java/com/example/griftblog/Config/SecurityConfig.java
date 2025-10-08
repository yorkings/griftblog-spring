package com.example.griftblog.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
    @Bean
    public  PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    };

    // ADD THIS BEAN to provide a temporary user
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("nyakz") // <--- Your new username
                .password(passwordEncoder().encode("west1234")) // <--- Your new password
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}



