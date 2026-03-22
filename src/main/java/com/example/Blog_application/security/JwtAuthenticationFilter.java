package com.example.Blog_application.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//    1. get token
        String requestToken = request.getHeader("Authorization"); // token will in the format like "Bearer qwwcewnjfbruik4tg78rh3iudbwfgi"
        System.out.println(requestToken);

        String username = null;
        String token = null;
        if(requestToken != null && requestToken.startsWith("Bearer ")){
            token = requestToken.substring(7);
            try{
                username = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token !!");
                throw new RuntimeException(e);
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired !!");
                throw new RuntimeException(e);
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT Token !!");
                throw new RuntimeException(e);
            }
        }
        else System.out.println("JWT Token doesn't begin with Bearer !!");

//        2. Once we get the token, now it's time to validate
        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){

            // 3. get user from token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtTokenHelper.validateToken(token, userDetails)){
                // 4. load user associated with user
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 5. setting spring security
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else System.out.println("Invalid JWT Token !!");
        }
        else System.out.println("Username is NULL && context is not NULL");

        filterChain.doFilter(request, response);

    }
}
