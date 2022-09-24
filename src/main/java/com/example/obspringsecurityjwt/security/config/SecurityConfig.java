package com.example.obspringsecurityjwt.security.config;

import com.example.obspringsecurityjwt.security.jwt.JwtAuthEntryPoint;
import com.example.obspringsecurityjwt.security.jwt.JwtRequestFilter;
import com.example.obspringsecurityjwt.security.service.UserDetailsServiceImpl;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Clase para la configuración de seguridad Spring Security
 */

@Configuration
@EnableWebSecurity  // permite a Spring aplicar esta configuración a la configuracion de seguridad global
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    // ============== CREACIÓN DE BEANS ==============

    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {

        return new JwtRequestFilter();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();

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


    // ======================== OVERRIDE: SOBREESCRIBIR FUNCIONALIDAD SECURITY POR DEFECTO ==============

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Cross-Site Request Forgery CSRF
        //CORS (Cross-origin resource sharing )

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/v2api-docs","/confugration/**","/swagger*/**","/webjars/**").permitAll()
                 .antMatchers("/").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
