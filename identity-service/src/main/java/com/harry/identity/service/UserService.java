package com.harry.identity.service;

import com.harry.event.dto.NotificationEmail;
import com.harry.identity.constant.PredefinedRole;
import com.harry.identity.dto.request.UserCreationRequest;
import com.harry.identity.dto.request.UserUpdateRequest;
import com.harry.identity.dto.response.UserCreationResponse;
import com.harry.identity.dto.response.UserResponse;
import com.harry.identity.entity.Role;
import com.harry.identity.entity.User;
import com.harry.identity.exception.AppException;
import com.harry.identity.exception.ErrorCode;
import com.harry.identity.mapper.UserMapper;
import com.harry.identity.repository.RoleRepository;
import com.harry.identity.repository.UserRepository;
import com.harry.identity.repository.httpClient.ImageClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    ImageClient imageClient;
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    KafkaTemplate<String, Object> kafkaTemplate;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();

        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        NotificationEmail notificationEmail = NotificationEmail.builder()
                .to(user.getEmail())
                .subject("Welcome to MyApp – Your registration is complete!")
                .text(String.format(String.format(
                                "Hi %s,\n\n" +
                                        "Welcome to MyApp! Your account has been created successfully.\n\n" +
                                        "Here’s what you can do next:\n" +
                                        "- Complete your profile so others can get to know you.\n" +
                                        "- Explore our Getting Started guide: https://myapp.com/docs/getting-started\n\n" +
                                        "If you have any questions or need help, reply to this email or reach us at support@myapp.com.\n\n" +
                                        "Cheers,\n" +
                                        "The Event Ticket Booking Flatform Team",
                                user.getUsername())))
                .build();

        // Publish message to kafka
        kafkaTemplate.send("notification-email-v3", notificationEmail);

        UserResponse userResponse = userMapper.toUserResponse(user);
        String msg = String.format("Congratulations on your successful account registration, please check your email");
        userResponse.setMessage(msg);
        log.info("User response after save: {}" + userResponse);
        return userResponse;
    }

    public UserResponse getMyInfo(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserResponse userResponse =  userMapper.toUserResponse(user);
        userResponse.setMessage(String.format("Successful information from customers %s", userResponse.getUsername()));
        return  userResponse;
    }


    public UserResponse updateUser(String userId, UserUpdateRequest request, MultipartFile avatar) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<MultipartFile> avatars = Collections.singletonList(avatar);
        List<String> listImgUrl = imageClient.uploadAvatar(avatars, "avatars");
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        String roleName = request.getRoles();
        var role = roleRepository.findById(roleName).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRoles(new HashSet<>(List.of(role)));
        user.setAvatar(listImgUrl.get(0));
        log.info("User : " + user);
        UserResponse userResponse = userMapper.toUserResponse(userRepository.save(user));
        String msg = String.format("Updated user information %s", userResponse.getUsername());
        userResponse.setMessage(msg);
        log.info("User Response: " + userResponse);
        return userResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(String userId, String reason ) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.deleteById(userId);
        String msg = String.format("Deleted user %s", user.getUsername());

        NotificationEmail notificationEmail = NotificationEmail.builder()
                .to(user.getEmail())
                .subject("Notice: Your account has been deleted from the system")
                .text(String.format(
                        "Hi %s,\n\n" +
                                "Your account has been deleted from the system.\n" +
                                "Reason: %s\n\n" +
                                "If you believe this is a mistake or have any questions, please contact us at support@myapp.com.\n\n" +
                                "Sincerely,\n" +
                                "The MyApp Team",
                        user.getUsername(),
                        reason
                ))
                .build();

        // Publish message to kafka
        kafkaTemplate.send("notification-email-v3", notificationEmail);
        return msg;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        List<UserResponse> listUserResponse =  userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
        return listUserResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        UserResponse userResponse = userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        userResponse.setMessage(String.format("Successful information from customers %s", userResponse.getUsername()));
        return userResponse;
    }
}
