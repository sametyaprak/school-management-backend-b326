package com.techproed.schoolmanagementbackendb326.securtiy.config;

import com.techproed.schoolmanagementbackendb326.securtiy.jwt.AuthEntryPointJwt;
import com.techproed.schoolmanagementbackendb326.securtiy.jwt.AuthTokenFilter;
import com.techproed.schoolmanagementbackendb326.securtiy.service.UserDetailServiceImpl;
import java.net.PasswordAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final UserDetailServiceImpl userDetailService;

  //diger pakette olusturdugumuz security exception handler
  private final AuthEntryPointJwt authEntryPointJwt;

  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //CORS cross*origin resource sharing
    http.cors()
        .and()
        .csrf().disable()
        //configure exception handler
    .exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
        .and()
        //configure session management
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //configure allow list
        .and()
        .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
        //other requests will be authenticated
        .anyRequest().authenticated();
        //configure frames to be sure from the same origin
        http.headers().frameOptions().sameOrigin();

  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }



  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter(){
    return new AuthTokenFilter();
  }


  private static final String[] AUTH_WHITELIST = {
      "/v3/api-docs/**",
      "swagger-ui.html",
      "/swagger-ui/**",
      "/",
      "index.html",
      "/images/**",
      "/css/**",
      "/js/**",
      "/contactMessages/save",
      "/auth/login"
  };



}
