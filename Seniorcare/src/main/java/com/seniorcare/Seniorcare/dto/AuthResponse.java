package com.seniorcare.Seniorcare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private Integer userId; // Trả về thêm userId để client tiện sử dụng
    private String fullName; // Trả về fullName để client hiển thị "Chào mừng..."
}