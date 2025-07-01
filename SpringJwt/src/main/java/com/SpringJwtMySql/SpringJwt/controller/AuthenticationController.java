package com.SpringJwtMySql.SpringJwt.controller;

import com.SpringJwtMySql.SpringJwt.entity.AuthenticationResponse;
import com.SpringJwtMySql.SpringJwt.entity.User;
import com.SpringJwtMySql.SpringJwt.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    //signup--------------------------------------------------------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }
//    Body of register endpoint
//    {
//            "firstName": "demo",
//            "lastName": "user",
//            "username": "demouser1",
//            "password": "demouser1",
//            "role": "USER"
//    }

    //login-----------------------------------------------------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
//    Body of login endpoint
//    {
//        "username": "demouser1",
//         "password": "demouser1"
//    }




//    @PostMapping("/refresh_token")
//    public ResponseEntity refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) {
//        return authService.refreshToken(request, response);
//    }
}
