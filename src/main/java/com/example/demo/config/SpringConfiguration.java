package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SpringConfiguration{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        HttpSecurity admin = http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/adminPanel").hasRole("ADMIN")
//                        .requestMatchers("/createMovie").hasRole("ADMIN")
//                        .requestMatchers("/editMovie/**").hasRole("ADMIN")
//                        .requestMatchers("/createReview/**").hasRole("ADMIN")
//                        .requestMatchers("/editReview/**").hasRole("ADMIN")
//                        .requestMatchers("/deleteMovie/**").hasRole("ADMIN")
//                        .requestMatchers("/deleteMovie/**").hasRole("ADMIN")
//                        .requestMatchers("/", "/movies/**", "/*/*.js", "/*/*.css").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form.loginPage("/login").permitAll())
//                .logout(Customizer.withDefaults())
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedPage("/index")
//                )
//                .csrf().disable();

        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/adminPanel").hasRole("ADMIN")
                .requestMatchers("/createMovie").hasRole("ADMIN")
                .requestMatchers("/editMovie/**").hasRole("ADMIN")
                .requestMatchers("/createReview/**").hasRole("ADMIN")
                .requestMatchers("/editReview/**").hasRole("ADMIN")
                .requestMatchers("/deleteMovie/**").hasRole("ADMIN")
                .requestMatchers("/deleteMovie/**").hasRole("ADMIN")
                .requestMatchers("/", "/movies/**", "/*/*.js", "/*/*.css").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .formLogin();


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
