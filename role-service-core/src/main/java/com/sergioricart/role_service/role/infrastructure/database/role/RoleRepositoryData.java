package com.sergioricart.role_service.role.infrastructure.database.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositoryData  extends JpaRepository<RoleEntity, String> {

}
