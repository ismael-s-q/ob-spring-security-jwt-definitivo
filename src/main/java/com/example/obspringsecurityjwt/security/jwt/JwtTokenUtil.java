package com.example.obspringsecurityjwt.security.jwt;


import antlr.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;


import javax.crypto.SecretKey;

import java.util.Date;

/**
 * Métodos para generar y validar los token JWT
 */
@Component
public class JwtTokenUtil {



    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;


    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateJwtToken(Authentication authentication){

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();


        return  Jwts.builder()   // constructor del token
                .setSubject((userPrincipal.getUsername()))   // agrega el usuario
                .setIssuedAt(new Date())   // agrega la fecha en que se genera
                .setExpiration(new Date ( (new Date()).getTime() + jwtExpirationMs))  // agrega la fecha en que ya no será válido
                .signWith(key)
                .compact();        // agrega el secreto que escogiste encriptado con HS512


    }

    public String getUserNameFromJwtToken(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }


    public boolean validateJwtToken(String authToken) {

      try {
          Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
          return true;
      }catch (SignatureException e) {
          log.error("Invalid JWt signature: {}", e.getMessage());
      }catch (MalformedJwtException e) {
          log.error("Invalid JWT token : {}", e.getMessage());
      }catch (ExpiredJwtException e ){
          log.error("JWT token is unsupported : {}", e.getMessage());
      }catch (UnsupportedJwtException e ) {
          log.error("JWT token is unsupported: {}", e.getMessage());
      }catch (IllegalArgumentException e) {
          log.error("JWT claims string is empty: {}", e.getMessage());
      }

        return false;
    }


}
