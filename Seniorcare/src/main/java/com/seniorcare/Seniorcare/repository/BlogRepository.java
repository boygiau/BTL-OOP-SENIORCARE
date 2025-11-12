package com.seniorcare.Seniorcare.repository;

import com.seniorcare.Seniorcare.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    // Chúng ta có thể thêm các hàm tìm kiếm tùy chỉnh ở đây sau
    // Ví dụ: List<Blog> findByCategory(BlogCategories category);
}