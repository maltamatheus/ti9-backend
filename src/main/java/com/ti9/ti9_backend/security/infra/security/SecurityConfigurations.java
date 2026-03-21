package com.ti9.ti9_backend.security.infra.security;

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

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/swagger-ui/**","/v3/api-docs/**")
                                    .permitAll()
                                .requestMatchers(HttpMethod.POST,"/auth/login")
                                    .permitAll()
                                .requestMatchers(HttpMethod.POST,"/auth/register","/fornecedores","/fornecedores/**")
                                    .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/fornecedores","/fornecedores/**")
                                    .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.POST,"/fornecedores")
//                                    .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.GET,"/fornecedores","/documentos","/avaliacoes")
//                                    .hasRole("VIEWER")
//                                .requestMatchers(HttpMethod.GET,"/analytics")
//                                    .hasRole("AUDITOR")
//                                .requestMatchers(HttpMethod.POST,"/auth/refresh")
//                                    .authenticated()
                                .anyRequest()
                                .authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
