package com.uyghur.java.hospital.hospital.security_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin();

        http.csrf().disable(); // should disable when using JWT STATELESS

        http
                .authorizeHttpRequests().requestMatchers("/home","/css/**", "/errors/**", "/img/**", "/webjars/**").permitAll();

//        http
//                .authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
//        http
//                .authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");


        http
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated();

//        http
//                .authorizeHttpRequests().requestMatchers("/delete/**").hasRole("ADMIN"); // order is important

        http.exceptionHandling().accessDeniedPage("/403");

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        String pwd = passwordEncoder().encode("12345");

        // User.withUsername("userA").password("{noop}12345").roles("USER").build()
        // User.withUsername("userA").password("12345").roles("USER").build() --> There is no PasswordEncoder mapped for the id "null"

        return new InMemoryUserDetailsManager(

                User.withUsername("userA").password(pwd).roles("USER").build(),
                User.withUsername("userB").password(pwd).roles("USER").build(),
                User.withUsername("admin").password(pwd).roles("USER", "ADMIN").build()

        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
