package com.ecommerce.server.repository;

import com.ecommerce.server.enums.RoleName;
import com.ecommerce.server.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName roleName);
}