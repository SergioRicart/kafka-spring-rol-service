package com.sergioricart.role_service.role.domain.port;

import com.sergioricart.role_service.role.domain.entity.Role;

public interface RoleRepository {

    void save(Role role);

}
