package com.harry.identity.mapper;

import com.harry.identity.dto.request.PermissionRequest;
import com.harry.identity.dto.response.PermissionResponse;
import com.harry.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
