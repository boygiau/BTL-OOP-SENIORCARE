package com.seniorcare.Seniorcare.service;

import com.seniorcare.Seniorcare.repository.BlogCategoriesRepository;
import com.seniorcare.Seniorcare.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogCategoriesRepository blogCategoriesRepository;

    // (Chúng ta sẽ thêm logic lấy danh sách blog, tạo blog... ở đây sau)
}