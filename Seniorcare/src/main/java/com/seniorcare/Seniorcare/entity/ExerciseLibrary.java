package com.seniorcare.Seniorcare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Exerciselibrary")
public class ExerciseLibrary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Integer exerciseId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "master_video_url")
    private String masterVideoUrl;

    @Column(name = "difficulty_level")
    private String difficultyLevel;

    @Column(name = "focus_area")
    private String focusArea;

    // --- Relationship 1: Tới ExerciseCategories ---
    // Nhiều ExerciseLibrary thuộc về 1 ExerciseCategories
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // Khóa ngoại trong bảng này
    private ExerciseCategories category;

    // --- Relationship 2: Tới bảng trung gian UserExerciseSessions ---
    // 1 ExerciseLibrary có thể xuất hiện trong nhiều UserExerciseSessions
    // 'exercise' là tên biến 'ExerciseLibrary exercise' trong class UserExerciseSession
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserExerciseSession> userSessions;
}