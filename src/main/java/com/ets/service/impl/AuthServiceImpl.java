package com.ets.service.impl;

import com.ets.Utils.JwtUtils;
import com.ets.dto.LoginRequest;
import com.ets.dto.LoginResponse;
import com.ets.model.Utilisateur;
import com.ets.repository.UtilisateurRepository;
import com.ets.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {

        Utilisateur user = utilisateurRepo.findByUsername(request.getUsername());
 
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponse(token);
    }
}