package com.seniorcare.Seniorcare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Integer contentId;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "content_body_or_url", nullable = false, columnDefinition = "TEXT")
    private String contentBodyOrUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "author")
    private String author;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    // --- Relationship ---
    // Nhiều Blog thuộc về 1 BlogCategory
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // Đây là khóa ngoại trong bảng 'Blog'
    private BlogCategories category;
}