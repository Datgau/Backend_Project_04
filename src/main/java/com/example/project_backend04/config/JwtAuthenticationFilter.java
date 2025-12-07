package com.example.project_backend04.config;

import com.example.project_backend04.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        System.out.println("=== JwtAuthenticationFilter ===");
        System.out.println("Path: " + requestPath);
        System.out.println("Method: " + request.getMethod());
        
        final String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + (authHeader != null ? authHeader.substring(0, Math.min(20, authHeader.length())) + "..." : "null"));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No Bearer token, allowing request to continue");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);
        System.out.println("Extracted username: " + username);

        // Chỉ set authentication nếu chưa có
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Loading user details for: " + username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            boolean isValid = jwtService.isTokenValid(jwt, username);
            System.out.println("Token valid: " + isValid);
            
            if (isValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("✅ Authentication set successfully for: " + username);
            } else {
                System.out.println("❌ Token is invalid");
            }
        } else if (username == null) {
            System.out.println("❌ Username is null");
        } else {
            System.out.println("Authentication already exists");
        }

        filterChain.doFilter(request, response);
    }
}
