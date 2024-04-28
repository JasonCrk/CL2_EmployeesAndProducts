package com.cibertec.CL2.security.filters;

import com.cibertec.CL2.models.EmployeeEntity;
import com.cibertec.CL2.repositories.EmployeeRepository;
import com.cibertec.CL2.repositories.SessionTokenRepository;
import com.cibertec.CL2.security.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SessionTokenRepository sessionTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String employeeUsername, accessToken;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        accessToken = authHeader.substring(7);
        employeeUsername = jwtUtils.extractUsername(accessToken);

        if (employeeUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(employeeUsername);

            Optional<EmployeeEntity> employee = this.employeeRepository.findByUsername(userDetails.getUsername());

            if (employee.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            boolean isAccessTokenValid = this.sessionTokenRepository.findByToken(accessToken)
                    .map(token -> !token.isExpired() && !token.isRevoked())
                    .orElse(false);

            if (jwtUtils.isTokenValid(accessToken, userDetails.getUsername()) && isAccessTokenValid) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
