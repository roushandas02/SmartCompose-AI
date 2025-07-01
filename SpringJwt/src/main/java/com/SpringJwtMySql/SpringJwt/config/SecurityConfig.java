package com.SpringJwtMySql.SpringJwt.config;

import com.SpringJwtMySql.SpringJwt.filter.JwtAuthenticationFilter;
import com.SpringJwtMySql.SpringJwt.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //just Autowiring-----------------------------------------------------------------------------------------
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final CustomLogoutHandler logoutHandler;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp,
                          JwtAuthenticationFilter jwtAuthenticationFilter
//                          ,CustomLogoutHandler logoutHandler
    ) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//        this.logoutHandler = logoutHandler;
    }


    //to add and configure the filter in the filter chain which protects endpoints...each http request first passes through filters(see diagram for clarity)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable) //csrf is for stateful web apps
                .authorizeHttpRequests(
                        req->req.requestMatchers("/login/**","/register/**", "/refresh_token/**")//these endpoints anyone can access
                                .permitAll()
                                .requestMatchers("/admin_only/**").hasAuthority("ADMIN")//only admin can access this
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsServiceImp) //UserDetailsService implementation used in our app
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))//make it stateless - A stateless application does not store any user session information on the server between HTTP requests.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) //It inserts your jwtAuthenticationFilter into Spring Security’s filter chain, before the built-in UsernamePasswordAuthenticationFilter.
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
//                .logout(l->l
//                        .logoutUrl("/logout")
//                        .addLogoutHandler(logoutHandler)
//                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
//                        ))
                .build();

//        🔹 accessDeniedHandler
//        → Returns 403 Forbidden if an authenticated user tries to access a resource without permission. Does not redirect or other actions.
//
//        🔹 authenticationEntryPoint
//        → Returns 401 Unauthorized if an unauthenticated user tries to access a protected resource. Does not redirect or other actions.
//
//        🔹 logoutUrl("/logout")
//        → Defines /logout as the endpoint to trigger logout.
//
//        🔹 addLogoutHandler(logoutHandler)
//        → Runs your custom logic (e.g., token cleanup) during logout.
//
//        🔹 logoutSuccessHandler
//        → Clears the security context after a successful logout.
    }

    //To encode the password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Authentication Manager authenticates the user and sends it to SecurityContextHolder as authentication object
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}