package com.harry.identity.mapper;

import com.harry.identity.dto.request.RoleRequest;
import com.harry.identity.dto.response.RoleResponse;
import com.harry.identity.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
