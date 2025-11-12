package com.seniorcare.Seniorcare.repository;

import com.seniorcare.Seniorcare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Tự động tạo query: "SELECT * FROM Users WHERE phone_number = ?"
    // Chúng ta sẽ dùng hàm này cho việc đăng nhập (Security)
    Optional<User> findByPhoneNumber(String phoneNumber);

    // Tự động tạo query: "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Users WHERE phone_number = ?"
    // Dùng để kiểm tra SĐT đã tồn tại khi đăng ký
    boolean existsByPhoneNumber(String phoneNumber);
}