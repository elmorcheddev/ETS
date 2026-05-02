package com.ets.service;

import com.ets.dto.LoginRequest;
import com.ets.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}