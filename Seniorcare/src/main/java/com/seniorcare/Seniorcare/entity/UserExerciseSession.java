package com.seniorcare.Seniorcare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Userexercisesessions")
public class UserExerciseSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;

    @Column(name = "session_date")
    private Timestamp sessionDate;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "performance_score")
    private BigDecimal performanceScore;

    @Column(name = "common_mistakes", columnDefinition = "TEXT")
    private String commonMistakes;

    @Column(name = "ai_feedback", columnDefinition = "TEXT")
    private String aiFeedback;

    // --- Relationship 1: Tới User ---
    // Nhiều Session thuộc về 1 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Khóa ngoại trong bảng này
    private User user;

    // --- Relationship 2: Tới ExerciseLibrary ---
    // Nhiều Session sử dụng 1 Exercise
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false) // Khóa ngoại trong bảng này
    private ExerciseLibrary exercise;
}