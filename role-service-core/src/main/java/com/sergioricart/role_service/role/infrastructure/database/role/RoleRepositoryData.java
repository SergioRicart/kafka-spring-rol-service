package com.sergioricart.role_service.role.infrastructure.database.role;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepositoryData extends JpaRepository<RoleEntity, String> {

    @EntityGraph(attributePaths = {"pages"})
    Optional<RoleEntity> findWithPagesById(String id);

}
