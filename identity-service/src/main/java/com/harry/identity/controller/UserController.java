package com.harry.identity.controller;

import com.harry.identity.dto.request.UserCreationRequest;
import com.harry.identity.dto.request.UserUpdateRequest;
import com.harry.identity.dto.response.ApiResponse;
import com.harry.identity.dto.response.UserCreationResponse;
import com.harry.identity.dto.response.UserResponse;
import com.harry.identity.service.UserService;
import com.harry.identity.validator.DobConstraint;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/registration")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        log.info("resgistration controller" + request);
        UserResponse userResponse = userService.createUser(request);
        return ApiResponse.<UserResponse>builder()
                .message(userResponse.getMessage())
                .result(userResponse)
                .build();
    }
    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        List<UserResponse>  listUserResponse = userService.getUsers();
        String msg = "Has successfully listed the user list";
        return ApiResponse.<List<UserResponse>>builder()
                .message(msg)
                .result(listUserResponse)
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        UserResponse userResponse = userService.getUser(userId);
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .message(userResponse.getMessage())
                .build();
    }

    @GetMapping("/my-info/{userId}")
    ApiResponse<UserResponse> getMyInfo(@PathVariable String userId) {
        log.info("My INFO CONTROLLER");

        UserResponse userResponse = userService.getMyInfo(userId);
        return ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .message(userResponse.getMessage())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId, @RequestBody String reason) {
        String msg = userService.deleteUser(userId, reason);
        return ApiResponse.<String>builder().result(msg).build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId,
                                         @RequestParam String firstName,
                                         @RequestParam String lastName,
                                         @RequestParam LocalDate dob,
                                         @RequestParam String roles,
                                         @RequestParam String password,
                                         @RequestParam MultipartFile avatar) {

        UserUpdateRequest request = new UserUpdateRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setDob(dob);
        request.setRoles(roles);
        request.setPassword(password);
        UserResponse userResponse = userService.updateUser(userId, request, avatar);
        return ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .message(userResponse.getMessage())
                .build();
    }
}
