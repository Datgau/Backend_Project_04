package com.example.project_backend04.service;

import com.example.project_backend04.dto.request.User.CreateUserRequest;
import com.example.project_backend04.dto.request.User.UpdateUserRequest;
import com.example.project_backend04.dto.response.Shared.ApiResponse;
import com.example.project_backend04.dto.response.User.UserResponse;
import com.example.project_backend04.entity.Role;
import com.example.project_backend04.entity.User;
import com.example.project_backend04.mapper.UserMapper;
import com.example.project_backend04.repository.IRepository.IRoleRepository;
import com.example.project_backend04.repository.IRepository.IUserRepository;
import com.example.project_backend04.service.IService.IGoogleCloudStorageService;
import com.example.project_backend04.service.IService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IGoogleCloudStorageService googleCloudStorageService;
    private final UserMapper userMapper;


    @Transactional
    public ApiResponse<UserResponse> uploadUserAvatar(Long userId, MultipartFile avatarFile) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return new ApiResponse<>(false, "User không tồn tại", null, 404);
            }

            User user = optionalUser.get();
            String oldAvatarUrl = user.getAvatar();
            if (oldAvatarUrl != null && !oldAvatarUrl.isBlank()) {
                try {
                    googleCloudStorageService.deleteFile(oldAvatarUrl);
                } catch (Exception e) {
                    System.err.println("[Warning] Không thể xóa avatar cũ: " + e.getMessage());
                }
            }
            String newAvatarUrl = googleCloudStorageService.uploadSingleFile(avatarFile, "avatars");
            user.setAvatar(newAvatarUrl);
            userRepository.save(user);

            return new ApiResponse<>(true, "Cập nhật avatar thành công", userMapper.toResponse(user), 200);
        } catch (IOException e) {
            return new ApiResponse<>(false, "Lỗi khi upload avatar: " + e.getMessage(), null, 500);
        }
    }

}
