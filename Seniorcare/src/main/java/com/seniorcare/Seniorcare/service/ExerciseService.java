package com.seniorcare.Seniorcare.service;

import com.seniorcare.Seniorcare.repository.ExerciseCategoriesRepository;
import com.seniorcare.Seniorcare.repository.ExerciseLibraryRepository;
import com.seniorcare.Seniorcare.repository.UserExerciseSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseCategoriesRepository exerciseCategoriesRepository;
    private final ExerciseLibraryRepository exerciseLibraryRepository;
    private final UserExerciseSessionRepository userExerciseSessionRepository;

    // (Chúng ta sẽ thêm logic lấy bài tập, lưu phiên tập... ở đây sau)
}