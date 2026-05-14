package com.sergioricart.role_service.role.domain.port;

import com.sergioricart.role_service.role.domain.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    void save(Role role);

    List<Role> findAll();

    Optional<Role> findById(String id);

    void deleteById(String id);

}
