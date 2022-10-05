package com.example.obspringsecurityjwt.security.config;

import com.example.obspringsecurityjwt.security.jwt.JwtAuthEntryPoint;
import com.example.obspringsecurityjwt.security.jwt.JwtRequestFilter;
import com.example.obspringsecurityjwt.security.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Clase para la configuración de seguridad Spring Security
 */

@Configuration
public class SecurityConfig  {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;


    // ============== CREACIÓN DE BEANS ==============





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {



        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/users/**", "/settings/**").hasAuthority("Admin")
               // .hasAnyAuthority("Admin", "Editor", "Salesperson")
              //  .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
                .and()
                .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
                .and()
                .logout().permitAll();

        http.headers().frameOptions().sameOrigin();

        return http.build();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }







    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    /**
     * Configuración global de CORS para toda la aplicación
     */



    @Bean
    CorsConfigurationSource corsConfigurationSource(){


        CorsConfiguration  configuration = new CorsConfiguration();

        // confuguration.setAllowedOrigins(List.of("http://localhost:4200","htttps;//angular-springboot-*.vercel.app"));

        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200","https;//angularapringboot1-beta.vercel.app"));
        configuration.setAllowedMethods(List.of("GET","POST","OPTIONS","DELETE","PUT","PATCH"));
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin","X-Requested-With","Origin","Content-Type","Accept","Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        return source;
    }






}
