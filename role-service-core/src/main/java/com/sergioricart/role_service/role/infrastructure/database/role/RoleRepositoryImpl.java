package com.sergioricart.role_service.role.infrastructure.database.role;

import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleRepositoryData roleRepositoryData;

    private final RoleEntityMapper roleDatabaseMapper;

    @Override
    public void save(Role role) {
        roleRepositoryData.save(roleDatabaseMapper.mapToRoleEntity(role));
    }

    @Override
    public List<Role> findAll() {
        return roleRepositoryData.findAllByDeletedAtIsNull()
                .stream()
                .map(roleDatabaseMapper::mapToRoleWithoutPages)
                .toList();
    }

    @Override
    public Optional<Role> findById(String id) {
        return roleRepositoryData.findWithPagesByIdAndDeletedAtIsNull(id)
                .map(roleDatabaseMapper::mapToRoleWithPages);
    }

    @Override
    public void deleteById(String id) {
        roleRepositoryData.deleteById(id, Instant.now());
    }
}
