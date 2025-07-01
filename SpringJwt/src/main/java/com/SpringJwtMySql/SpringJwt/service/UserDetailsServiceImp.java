package com.SpringJwtMySql.SpringJwt.service;

import com.SpringJwtMySql.SpringJwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    //UserDetailsService is a Spring Security interface whose job is to load user-specific data from your database or any source, by username.
    //It has only 1 function - UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    private final UserRepository repository;

    public UserDetailsServiceImp(UserRepository repository) {
        this.repository = repository;
    }
//    or
//    @Autowired
//    private UserRepository userRepository;


    //overrides the only one function present in UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

}
//Why can’t we “directly” use UserRepository in place of UserDetailsService?
//Because Spring Security doesn’t know about your repository.
//It only knows how to call UserDetailsService.loadUserByUsername() to get the UserDetails object.
//
//Spring Security expects an instance of UserDetailsService.
//
//This is how it is wired internally.
//When you configure Spring Security (e.g., with AuthenticationManagerBuilder or SecurityFilterChain), you tell it:
//
//Here’s the UserDetailsService you must call to load user details.
//
//It doesn’t know or care how you actually implement that behind the scenes (JPA, JDBC, LDAP, etc).