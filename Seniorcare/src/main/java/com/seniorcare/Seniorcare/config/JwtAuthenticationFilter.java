package com.seniorcare.Seniorcare.config;

import com.seniorcare.Seniorcare.service.CustomUserDetailsService;
import com.seniorcare.Seniorcare.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Đánh dấu đây là một Bean
@RequiredArgsConstructor


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userPhoneNumber; // SĐT người dùng

        // 1. Kiểm tra xem header có "Authorization" và "Bearer " không
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Nếu không có, cho qua
            return;
        }

        // 2. Lấy token từ header (sau chữ "Bearer ")
        jwt = authHeader.substring(7);
        userPhoneNumber = jwtService.extractUsername(jwt); // Giải mã token



        // 3. Nếu có SĐT và user chưa được xác thực trong SecurityContext
        if (userPhoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Lấy thông tin User (UserDetails) từ CSDL
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userPhoneNumber);

            // 4. Kiểm tra token có hợp lệ không
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Nếu hợp lệ, tạo một phiên xác thực
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Lưu phiên xác thực vào SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response); // Chuyển request cho filter tiếp theo
    }
}