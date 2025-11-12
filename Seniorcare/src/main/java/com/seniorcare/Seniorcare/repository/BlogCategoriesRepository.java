package com.seniorcare.Seniorcare.repository;

import com.seniorcare.Seniorcare.entity.BlogCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCategoriesRepository extends JpaRepository<BlogCategories, Integer> {
}