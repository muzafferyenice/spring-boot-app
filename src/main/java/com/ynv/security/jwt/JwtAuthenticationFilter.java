package com.ynv.security.jwt;

import com.ynv.security.service.UserDetailsImpl;
import com.ynv.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private String extractJwtFromRequest(HttpServletRequest request){
        String headerAuth= request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))
            return headerAuth.substring(7);
        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwtToken = extractJwtFromRequest(request);
            if (StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)){
                Long id = jwtTokenProvider.getUserIdFromJWT(jwtToken);

                UserDetailsImpl user =userDetailsService.loadUserById(id);

                if (user != null){
                    UsernamePasswordAuthenticationToken auth=new
                            UsernamePasswordAuthenticationToken(user,null
                    ,user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch (Exception e){
            return;
        }
        filterChain.doFilter(request , response);






    }
}
