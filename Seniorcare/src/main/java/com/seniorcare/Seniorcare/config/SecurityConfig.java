package com.seniorcare.Seniorcare.config;

import com.seniorcare.Seniorcare.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Bật Spring Security
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    // Đây là Bean để cấu hình các quy tắc bảo mật
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF vì chúng ta dùng API (stateless)
                .authorizeHttpRequests(auth -> auth
                        // Cho phép tất cả mọi người truy cập API Đăng ký/Đăng nhập
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // (Bạn có thể thêm các API công khai khác ở đây)
                        // .requestMatchers("/api/v1/blogs/public").permitAll()

                        // Bất kỳ request nào khác đều phải được xác thực (phải có token)
                        .anyRequest().authenticated()
                )
                // Cấu hình Session là STATELESS (không dùng session)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()) // Cung cấp cơ chế xác thực
                // Thêm bộ lọc JWT của chúng ta vào trước bộ lọc UsernamePassword
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // --- ĐÂY LÀ 2 BEAN SỬA LỖI CỦA BẠN ---

    /**
     * Bean 1: Cung cấp PasswordEncoder (Bộ mã hóa mật khẩu)
     * Đây là Bean mà AuthService của bạn đang thiếu.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean 2: Cung cấp AuthenticationProvider
     * (Nó sẽ dùng CustomUserDetailsService và PasswordEncoder)
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Dùng service của chúng ta
        authProvider.setPasswordEncoder(passwordEncoder()); // Dùng bộ mã hóa của chúng ta
        return authProvider;
    }

    /**
     * Bean 3: Cung cấp AuthenticationManager
     * Đây là Bean thứ 2 mà AuthService của bạn đang thiếu.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}