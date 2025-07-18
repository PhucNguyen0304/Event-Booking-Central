package com.harry.indentity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.harry.indentity_service.dto.request.RoleRequest;
import com.harry.indentity_service.dto.response.RoleResponse;
import com.harry.indentity_service.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
