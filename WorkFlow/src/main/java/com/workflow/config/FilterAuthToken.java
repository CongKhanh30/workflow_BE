package com.workflow.config;

import com.workflow.controller.AccountController;
import com.workflow.service.impl.AccountServiceImpl;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FilterAuthToken extends OncePerRequestFilter {

    private final AccountServiceImpl accountService;

    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }

    public String getUserNameFromJwtToken(String token) {
        String userName = Jwts.parser()
                .setSigningKey(AccountController.PRIVATE_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userName;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().startsWith("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = getTokenFromRequest(request);
            if (token != null) {
                String username = getUserNameFromJwtToken(token);

                UserDetails userDetails = accountService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println("failed on set user authentication");
        }
        filterChain.doFilter(request, response);
    }
}
