package com.cibertec.CL2.services.auth;

import com.cibertec.CL2.models.requests.LoginRequest;
import com.cibertec.CL2.models.responses.AuthTokensResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AuthTokensResponse login(LoginRequest request);
    AuthTokensResponse refreshToken(HttpServletRequest request, HttpServletResponse response);
}
