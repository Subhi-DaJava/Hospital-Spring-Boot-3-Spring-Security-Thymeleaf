package com.uyghur.java.hospital.hospital.security_config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


/**
 * Create User
 * Pwd: 12345
 */

@RestController
public class JDBC_UserManager {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public JDBC_UserManager(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder) {
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/jdbc-user")
    public void createUser() {
        String userA = "userA";
        String userB = "userB";
        String admin = "admin";
        jdbcUserDetailsManager.createUser(
                User.withUsername(userA).password(passwordEncoder.encode("12345")).roles("USER").build()

        );
        jdbcUserDetailsManager.createUser(
                User.withUsername(userB).password(passwordEncoder.encode("12345")).roles("USER").build()

        );
        jdbcUserDetailsManager.createUser(
                User.withUsername(admin).password(passwordEncoder.encode("12345")).roles("USER", "ADMIN").build()
        );
    }

    @GetMapping("/jdbc-user-authorities")
    public void createUserWithAuthorities() {
        String userE = "userE";
        String userF = "userF";
        String adminC = "adminC";
        jdbcUserDetailsManager.createUser(
                User.withUsername(userE).password(passwordEncoder.encode("12345")).authorities("USER").build()

        );
        jdbcUserDetailsManager.createUser(
                User.withUsername(userF).password(passwordEncoder.encode("12345")).authorities("USER").build()

        );
        jdbcUserDetailsManager.createUser(
                User.withUsername(adminC).password(passwordEncoder.encode("12345")).authorities("USER", "ADMIN").build()
        );
    }

    @GetMapping("/add-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addRole(@RequestParam(name = "newRole", defaultValue = "ROLE_USER") String newRole, @RequestParam(name="username") String username) {

        User userDetails = (User) jdbcUserDetailsManager.loadUserByUsername(username);

        GrantedAuthority newAuthority = new SimpleGrantedAuthority(newRole);

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(userDetails.getAuthorities());

        updatedAuthorities.add(newAuthority);

        User updatedUser = new User(userDetails.getUsername(), userDetails.getPassword(), updatedAuthorities);
        jdbcUserDetailsManager.updateUser(updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/principal")
    public Principal getPrincipal(Principal principal) {
        return principal;
    }

    @GetMapping("/security-context-holder")
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/username-pwd-auth-token")
    public UsernamePasswordAuthenticationToken getUsernamePwdAuthenticationToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UsernamePasswordAuthenticationToken) authentication;
    }
}
