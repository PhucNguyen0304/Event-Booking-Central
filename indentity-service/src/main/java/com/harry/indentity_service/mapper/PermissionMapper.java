package com.harry.indentity_service.mapper;

import org.mapstruct.Mapper;

import com.harry.indentity_service.dto.request.PermissionRequest;
import com.harry.indentity_service.dto.response.PermissionResponse;
import com.harry.indentity_service.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission request);
}
