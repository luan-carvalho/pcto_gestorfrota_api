package br.edu.ifto.gestorfrotaapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/**").permitAll())
                .rememberMe(rememberMe -> rememberMe
                        .key("Q1b7UzLYkZ++kJaZGapB/r/SVn4Xq3tvG3ECXElGr1w=")
                        .tokenValiditySeconds(86400));

        return http.build();
    }

}
