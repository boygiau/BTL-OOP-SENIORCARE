package com.seniorcare.Seniorcare.service;

import com.seniorcare.Seniorcare.dto.UserProfileDTO;
import com.seniorcare.Seniorcare.entity.User;
import com.seniorcare.Seniorcare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
// (Chúng ta sẽ cần thêm SecurityContextHolder để lấy user đã đăng nhập)

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // (Đây là code ví dụ - chúng ta sẽ cải thiện sau)
    public UserProfileDTO getUserProfile(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        return UserProfileDTO.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .hometown(user.getHometown())
                .currentAddress(user.getCurrentAddress())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }
}