package com.seniorcare.Seniorcare.service;

import com.seniorcare.Seniorcare.dto.AuthRequest;
import com.seniorcare.Seniorcare.dto.AuthResponse;
import com.seniorcare.Seniorcare.dto.SignUpRequest;
import com.seniorcare.Seniorcare.entity.User;
import com.seniorcare.Seniorcare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    // (LỖI ĐỎ - Sẽ sửa ở bước config)
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // Logic Đăng Ký
    public AuthResponse register(SignUpRequest request) {
        // Kiểm tra xem SĐT đã tồn tại chưa
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .passwordHash(passwordEncoder.encode(request.getPassword())) // Mã hóa mật khẩu
                .build();

        userRepository.save(user);

        // Tạo token cho user mới
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    // Logic Đăng Nhập
    public AuthResponse authenticate(AuthRequest request) {
        // Xác thực SĐT và Mật khẩu
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhoneNumber(),
                        request.getPassword()
                )
        );

        // Nếu xác thực thành công, tìm User
        var user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("Lỗi xác thực"));

        // Tạo token
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}