package com.seniorcare.Seniorcare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {

    private Integer userId;
    private String fullName;
    private Date dateOfBirth;
    private String phoneNumber;
    private String hometown;
    private String currentAddress;
    private String profilePictureUrl;
    private String hobbies;
    private String personalStory;
    private String emergencyContactPhone;
    private String emergencyContactEmail;

    // Chúng ta sẽ có một hàm (mapper) để chuyển đổi từ User (Entity) sang UserProfileDto (DTO)
}