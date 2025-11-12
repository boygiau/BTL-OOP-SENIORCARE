package com.seniorcare.Seniorcare.controller;

import com.seniorcare.Seniorcare.dto.AuthRequest;
import com.seniorcare.Seniorcare.dto.AuthResponse;
import com.seniorcare.Seniorcare.dto.SignUpRequest;
import com.seniorcare.Seniorcare.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth") // Đây là đường dẫn API chung
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * API để đăng ký tài khoản mới
     * POST /api/v1/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody SignUpRequest request
    ) {
        // Gọi service, service sẽ trả về token
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * API để đăng nhập
     * POST /api/v1/auth/authenticate
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) {
        // Gọi service, service sẽ trả về token
        return ResponseEntity.ok(authService.authenticate(request));
    }
}