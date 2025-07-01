package com.SpringJwtMySql.SpringJwt.filter;

import com.SpringJwtMySql.SpringJwt.service.JwtService;
import com.SpringJwtMySql.SpringJwt.service.UserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //just autowiring JwtService and UserDetailsServiceImp----------------------------------------
    private final JwtService jwtService;
    private final UserDetailsServiceImp userDetailsService;


    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImp userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    //Overriding OncePerRequestFilter class function-----------------------------------------------
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        //Getting the header Authorization field
        String authHeader = request.getHeader("Authorization");
        //This request doesn’t carry a JWT. We skip JWT authentication and continue processing the filter chain.
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        //extracting token from header if present
        String token = authHeader.substring(7);
        //extracting username from token using JwtService class function
        String username = jwtService.extractUsername(token);

        //if username is not null and user is not already authenticated
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //get the user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //If token is still valid
            if(jwtService.isValid(token, userDetails)) {
                //Spring Security uses UsernamePasswordAuthenticationToken to represent an authenticated user.
                //You create this and set it into the SecurityContextHolder

                //Creating an authentication object - authToken to be stored in SecurityContextHolder
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //pass to other filters of filterchain
        filterChain.doFilter(request, response);
    }
}
//in every http request, JWT token is sent in header in "Authorization" field
//Authorization - Bearer <JWT_TOKEN>

//After Authentication, the authentication object of user is kept in the SecurityContextHolder