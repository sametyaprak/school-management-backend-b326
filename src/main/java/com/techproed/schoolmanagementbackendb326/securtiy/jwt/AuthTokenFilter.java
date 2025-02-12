package com.techproed.schoolmanagementbackendb326.securtiy.jwt;

import com.techproed.schoolmanagementbackendb326.securtiy.service.UserDetailServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailServiceImpl userDetailService;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      //1-from every request, we will get JWT
      String jwt = parseJwt(request);
      //validate JWT
      if(jwt != null && jwtUtils.validateToken(jwt)) {
        //3- we need username to get user information
         String username = jwtUtils.getUsernameFromToken(jwt);
         //4- check DB and fetch user and upgrade it to userDetails
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        //5- set attribute with username
        request.setAttribute("username", username);
      }
    }
  }

  //Authorization -> Bearer ljsdfnkltskdfnvszlkfnvaqqdfknvaefkdsnvsacdfjknvcaldknsvcal
  private String parseJwt(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }
}
