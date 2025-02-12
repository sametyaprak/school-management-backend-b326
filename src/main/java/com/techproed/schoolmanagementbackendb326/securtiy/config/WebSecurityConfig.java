package com.techproed.schoolmanagementbackendb326.securtiy.config;

import com.techproed.schoolmanagementbackendb326.securtiy.jwt.AuthEntryPointJwt;
import com.techproed.schoolmanagementbackendb326.securtiy.jwt.AuthTokenFilter;
import com.techproed.schoolmanagementbackendb326.securtiy.service.UserDetailServiceImpl;
import java.net.PasswordAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        //configure authentication provider
        http.authenticationProvider(authenticationProvider());
        //configure JWT token hanler
        http.addFilterBefore(authenticationJwtTokenFilter(),
            UsernamePasswordAuthenticationFilter.class);
        return http.build();
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

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }



  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            //we let all sources to call our APIs
            .allowedOrigins("*")
            .allowedHeaders("*")
            .allowedMethods("*");
      }
    };
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
