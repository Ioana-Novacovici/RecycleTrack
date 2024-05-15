package com.GreenCycleSolutions.gcsbackend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/swagger-ui/**", "/gcs-openapi/**", "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/addresses/**").hasAuthority("ADMIN")
                        .requestMatchers("/users/**").hasAuthority("ADMIN")
                        .requestMatchers("/collections/use", "collections/weekly").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST,"/collections/**").hasAuthority("AGENT")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
