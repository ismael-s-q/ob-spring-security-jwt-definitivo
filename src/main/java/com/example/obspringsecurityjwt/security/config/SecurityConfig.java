package com.example.obspringsecurityjwt.security.config;

import com.example.obspringsecurityjwt.security.jwt.JwtAuthEntryPoint;
import com.example.obspringsecurityjwt.security.jwt.JwtRequestFilter;
import com.example.obspringsecurityjwt.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Clase para la configuración de seguridad Spring Security
 */

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

   // @Autowired
    private UserDetailsServiceImpl userDetailsService;

   // @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;


    // ============== CREACIÓN DE BEANS ==============





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {



        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                .antMatchers("/users/**", "/settings/**").hasAuthority("Admin")
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers("/api/hello/**").permitAll()
                .antMatchers("/").permitAll()
                // .hasAnyAuthority("Admin", "Editor", "Salesperson")
                //  .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                .anyRequest().authenticated().and()
                .authenticationManager(authenticationManager(new AuthenticationConfiguration()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);



        http.headers().frameOptions().sameOrigin();

        return http.build();


    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    /**
     * Configuración global de CORS para toda la aplicación
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration  configuration = new CorsConfiguration();



        // configuration.setAllowedOriginPatterns(List.of("http://localhost:4200","https://angular-springboot-*.vercel.app"));

        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200","angular-springboot1-sable.vercel.app"));
        configuration.setAllowedMethods(List.of("GET","POST","OPTIONS","DELETE","PUT","PATCH"));
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin","X-Requested-With","Origin","Content-Type","Accept","Authorization"));
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }






}
