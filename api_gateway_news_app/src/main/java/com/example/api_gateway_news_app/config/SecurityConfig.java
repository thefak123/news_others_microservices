package com.example.api_gateway_news_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.api_gateway_news_app.config.CustomMessage.CustomAuthenticationEntryPoint;
import com.example.api_gateway_news_app.filter.JWTAuthenticationFilter;
import com.example.api_gateway_news_app.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private UserService userDetailsService;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // .requestMatchers("v1/login/**", "v1/register/**").permitAll().anyRequest().authenticated()
        // request.requestMatchers("/auth/**").permitAll().requestMatchers("/comments").authenticated()
        // request.requestMatchers("/auth/**").permitAll().anyRequest().authenticated()
        return httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, "/comments/**").authenticated().anyRequest().permitAll())
        .exceptionHandling(exc -> exc.authenticationEntryPoint(customAuthenticationEntryPoint)).csrf(csrf -> csrf.disable())
        // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) means doesn't use http session to store security information
        .userDetailsService(userDetailsService).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) means attempt jwtAuthenticationFilter first
        // Before go into UsernamePasswordAuthenticationFilter. Because at jwtAuthenticationFilter, it sets the username, password (user detail) etc
        // Which are required in the  UsernamePasswordAuthenticationFilter
        // jwtAuthenticationFilter will be executed whether the target route is protected or not
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    

    @Bean
    // Defines a bean for AuthenticationManager, which is used to process authentication requests. It is obtained from the AuthenticationConfiguration
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    
}
