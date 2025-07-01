package com.SpringJwtMySql.SpringJwt.service;

import com.SpringJwtMySql.SpringJwt.entity.User;
//import com.SpringJwtMySql.SpringJwt.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey; //Key we will be using to sign and validate token

    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpire;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;


//    private final TokenRepository tokenRepository;

//    public JwtService(TokenRepository tokenRepository) {
//        this.tokenRepository = tokenRepository;
//    }

    //verifies tokens and get all claims like - subject, issuedAt, expiration, etc.-----------
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //This is a generic helper method to flexibly extract any information from a JWT.--------
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    //Functional Interface Function Takes Claims and returns T type of claim from claims

    //Extracting username and Expiration
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    ///check if Access token && Refresh token is valid -------------------------------------------------
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

//        boolean validToken = tokenRepository
//                .findByAccessToken(token)
//                .map(t -> !t.isLoggedOut())
//                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token)
//                && validToken
                ;
    }

//    public boolean isValidRefreshToken(String token, User user) {
//        String username = extractUsername(token);
//
//        boolean validRefreshToken = tokenRepository
//                .findByRefreshToken(token)
//                .map(t -> !t.isLoggedOut())
//                .orElse(false);
//
//        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;
//    }




    //Generate Access Token & Refresh Token-----------------------------------------------------
    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpire);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpire );
    }


    //Generate token----------------------------------------------------------------------------
    private String generateToken(User user, long expireTime) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime ))
                .signWith(getSigninKey())
                .compact();

        return token;
    }
    //first base64 encoded version of jwt written and then decoded to get normal text
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
//JWT has = Header (signing algorithms) Payload (user details) Signatutre (re signature of header, payload and jwtSecret)
//Benefits of JWT over others
//
//1. Stateless - Traditional sessions store user data on the server (e.g., in memory or Redis). JWT stores all user info in the token itself.
//2. Cross Domain / Cross Service - Because JWT is just a signed token, you can pass it: between microservices, across domains, between frontend and backend.
//3. You store the token client-side (e.g., in localStorage or Authorization header).
//4. Tokens include an expiry timestamp (exp).
//5. Self Contained Claims - All the required username, details, etc is in the payload only. Fast authorization without hitting the database every time.
//6. URl safe characters used in token. can be sent by url also.