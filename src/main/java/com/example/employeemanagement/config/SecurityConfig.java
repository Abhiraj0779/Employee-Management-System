package com.example.employeemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/employees").hasRole("ADMIN")
                .requestMatchers("/employees/new", "/employees/*/edit").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/employees/*/delete").hasRole("ADMIN")
                .anyRequest().authenticated())
            .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/employees", true).permitAll())
            .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var admin = User.withUsername("admin").password(encoder.encode("admin123")).roles("ADMIN").build();
        var viewer = User.withUsername("viewer").password(encoder.encode("viewer123")).roles("VIEWER").build();
        return new InMemoryUserDetailsManager(admin, viewer);
    }
}
