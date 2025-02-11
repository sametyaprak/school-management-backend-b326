package com.techproed.schoolmanagementbackendb326.securtiy.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


  @Value("${backendapi.app.jwtExpirationMs}")
  private long jwtExpirations;

  @Value("${backendapi.app.jwtSecret}")
  private String jwtSecret;


  private String buildTokenFromUsername(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime()+jwtExpirations))
        .signWith(SignatureAlgorithm.HS512,jwtSecret)
        .compact();
  }



}
