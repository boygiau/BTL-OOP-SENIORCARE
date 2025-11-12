package com.seniorcare.Seniorcare.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm; // <-- Thêm lại import này
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.security.jwt.expiration}")
    private long EXPIRATION_TIME;

    // Trích xuất username (số điện thoại) từ token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Trích xuất một "claim" (thông tin) cụ thể từ token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Tạo token chỉ từ UserDetails
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // --- ĐÃ TRẢ LẠI (REVERT) VỀ CÚ PHÁP CŨ 0.11.5 ---
    // (Hàm này có thể báo vàng "deprecated", nhưng sẽ hết lỗi đỏ)
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims) // Cú pháp cũ
                .setSubject(userDetails.getUsername()) // Cú pháp cũ
                .setIssuedAt(new Date(System.currentTimeMillis())) // Cú pháp cũ
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Cú pháp cũ
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // <-- Cú pháp cũ
                .compact();
    }

    // --- BỔ SUNG HÀM NÀY VÀO JWTSERVICE.JAVA ---

    // Kiểm tra xem token có hợp lệ không (đúng user và chưa hết hạn)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Kiểm tra token đã hết hạn chưa
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Lấy ngày hết hạn từ token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // --- ĐÃ TRẢ LẠI (REVERT) VỀ CÚ PHÁP CŨ 0.11.5 ---
    // (Hàm này sẽ hết lỗi đỏ 'verifyWith')
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder() // <-- Dùng parserBuilder()
                .setSigningKey(getSignInKey()) // <-- Dùng setSigningKey()
                .build()
                .parseClaimsJws(token) // <-- Dùng parseClaimsJws()
                .getBody();
    }

    // Lấy khóa bí mật (secret key) từ file properties
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}