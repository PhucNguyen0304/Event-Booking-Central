package com.harry.identity.mapper;

import com.harry.identity.dto.request.UserCreationRequest;
import com.harry.identity.dto.request.UserUpdateRequest;
import com.harry.identity.dto.response.UserCreationResponse;
import com.harry.identity.dto.response.UserResponse;
import com.harry.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}