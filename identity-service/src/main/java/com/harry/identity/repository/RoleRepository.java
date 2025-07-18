package com.harry.identity.repository;

import com.harry.identity.entity.Role;
import com.harry.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String rolename);
}
