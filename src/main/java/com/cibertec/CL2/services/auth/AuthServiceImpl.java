package com.cibertec.CL2.services.auth;

import com.cibertec.CL2.models.EmployeeEntity;
import com.cibertec.CL2.models.requests.LoginRequest;
import com.cibertec.CL2.models.responses.AuthTokensResponse;
import com.cibertec.CL2.repositories.EmployeeRepository;
import com.cibertec.CL2.security.JwtUtils;
import com.cibertec.CL2.services.sessionToken.SessionTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtils jwtUtils;

    private final SessionTokenService sessionTokenService;

    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthTokensResponse login(LoginRequest request) {
        EmployeeEntity employee = this.employeeRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El empleado no existe"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                employee.getUsername(),
                request.getKey()
        ));

        String accessToken = this.jwtUtils.generateToken(employee);
        String refreshToken = this.jwtUtils.generateRefreshToken(employee);

        this.sessionTokenService.revokeAllEmployeeTokens(employee);
        this.sessionTokenService.saveEmployeeToken(employee, accessToken);

        return new AuthTokensResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public AuthTokensResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        final String refreshToken;
        final String username;

        if (authHeader == null ||!authHeader.startsWith("Bearer "))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No se encuentra el token de actualización o la cabecera");

        refreshToken = authHeader.substring(7);

        username = jwtUtils.extractUsername(refreshToken);

        if (username == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El token de actualización es invalido");

        var employee = this.employeeRepository.findByUsername(username)
                .orElseThrow();

        if (!jwtUtils.isTokenValid(refreshToken, employee.getUsername()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El token de actualización es invalido");

        var accessToken = jwtUtils.generateToken(employee);

        this.sessionTokenService.revokeAllEmployeeTokens(employee);
        this.sessionTokenService.saveEmployeeToken(employee, accessToken);

        return new AuthTokensResponse(accessToken, refreshToken);
    }
}
