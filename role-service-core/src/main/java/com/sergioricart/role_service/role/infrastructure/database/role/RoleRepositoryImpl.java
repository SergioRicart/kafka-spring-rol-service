package com.sergioricart.role_service.role.infrastructure.database.role;

import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleRepositoryData roleRepositoryData;

    private final RoleEntityMapper roleDatabaseMapper;

    @Override
    public void save(Role role) {
        roleRepositoryData.save(roleDatabaseMapper.mapToRoleEntity(role));
    }
}
