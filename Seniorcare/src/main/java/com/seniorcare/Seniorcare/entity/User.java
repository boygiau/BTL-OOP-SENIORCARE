package com.seniorcare.Seniorcare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // <-- Thêm import

import java.sql.Timestamp;
import java.util.Collection; // <-- Thêm import
import java.util.Collections; // <-- Thêm import
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User implements UserDetails { // <-- Sửa ở đây

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "hometown")
    private String hometown;

    @Column(name = "current_address", columnDefinition = "TEXT")
    private String currentAddress;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "hobbies", columnDefinition = "TEXT")
    private String hobbies;

    @Column(name = "personal_story", columnDefinition = "TEXT")
    private String personalStory;

    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    @Column(name = "emergency_contact_email")
    private String emergencyContactEmail;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    // --- Relationship ---
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserExerciseSession> exerciseSessions;

    // --- CÁC HÀM CỦA UserDetails ---
    // (Chúng ta sẽ implement các hàm này)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Chúng ta chưa dùng Roles (quyền), nên trả về danh sách rỗng
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        // Spring Security sẽ dùng hàm này để lấy mật khẩu
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        // Spring Security sẽ dùng SĐT làm username
        return this.phoneNumber;
    }

    // Các hàm dưới đây chúng ta mặc định là true (tài khoản hợp lệ)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}