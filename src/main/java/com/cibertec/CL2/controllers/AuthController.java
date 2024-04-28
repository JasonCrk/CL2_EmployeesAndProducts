package com.cibertec.CL2.controllers;

import com.cibertec.CL2.models.requests.LoginRequest;
import com.cibertec.CL2.models.responses.AuthTokensResponse;
import com.cibertec.CL2.services.auth.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthTokensResponse> login(
            @Valid @RequestBody LoginRequest loginData
    ) {
        return ResponseEntity.ok(this.service.login(loginData));
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<AuthTokensResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(this.service.refreshToken(request, response));
    }
}
