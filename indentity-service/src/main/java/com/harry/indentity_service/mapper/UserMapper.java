package com.harry.indentity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.harry.indentity_service.dto.request.UserCreationRequest;
import com.harry.indentity_service.dto.request.UserUpdateRequest;
import com.harry.indentity_service.dto.response.UserResponse;
import com.harry.indentity_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
