package com.healthPharmacy.demo.infra.security;

import com.healthPharmacy.demo.infra.exception.handlers.CustomAccessDeniedHandler;
import com.healthPharmacy.demo.infra.exception.handlers.CustomAuthenticationEntryPoint;
import com.healthPharmacy.demo.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    private final LoginService loginService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomAuthenticationEntryPoint authEntryPoint,
                                                   CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // AUTH
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()

                        // CUSTOMERS
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/registration").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/customers/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**").hasAuthority("ROLE_CUSTOMER")

                        // EMPLOYEES
                        .requestMatchers(HttpMethod.POST, "/api/v1/employee/registration").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/employee/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/employee/**").hasAuthority("ROLE_ADMIN")

                        // PRODUCTS
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/supplements").hasAuthority("ROLE_EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/medications").hasAuthority("ROLE_EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/hygieneProducts").hasAuthority("ROLE_EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/cosmetics").hasAuthority("ROLE_EMPLOYEE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAuthority("ROLE_EMPLOYEE")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/products/**").hasAuthority("ROLE_EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()

                        // ORDERS
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orders/cart/decrease").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders/checkout").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders/cart/add").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders/buy-now").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/my-orders").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/cart").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/cart/remove").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasAuthority("ROLE_CUSTOMER")

                        // SWAGGER / DOCS
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()

                        // DEFAULT
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return loginService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}