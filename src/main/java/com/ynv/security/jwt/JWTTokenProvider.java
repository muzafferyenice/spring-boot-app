package com.ynv.security.jwt;

import com.ynv.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


@Component
public class JWTTokenProvider {

    @Value("${ynv.app.jwtSecret}")
    private String APP_SECRET;

    @Value("${ynv.app.jwtExpirationMs}")
    private long EXPIRES_IN;

    public String generateJWTToken(Authentication auth){
       UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        Date expireDate = new Date(System.currentTimeMillis()+ EXPIRES_IN);

       return Jwts.builder().
               setSubject(userDetails.getId().toString())
               .setIssuer("ynv")
               .setExpiration(expireDate)
               .signWith(SignatureAlgorithm.HS512,APP_SECRET).
               compact();

    }

    Long getUserIdFromJWT(String token){
        Claims claims =Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    private boolean isTokenExpired(String token){
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }


    boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        }catch (SignatureException e){
            return false;
        }catch (MalformedJwtException e){
            return false;
        }catch (ExpiredJwtException e){
            return false;
        }catch (UnsupportedJwtException e){
            return false;
        }catch (IllegalArgumentException e){
            return false;
        }
    }
}
