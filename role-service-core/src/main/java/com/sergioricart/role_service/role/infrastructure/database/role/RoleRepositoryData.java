package com.sergioricart.role_service.role.infrastructure.database.role;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepositoryData extends JpaRepository<RoleEntity, String> {

    List<RoleEntity> findAllByDeletedAtIsNull();

    @EntityGraph(attributePaths = {"pages"})
    Optional<RoleEntity> findWithPagesByIdAndDeletedAtIsNull(String id);

    @Modifying
    @Query("UPDATE RoleEntity r SET r.deletedAt = :deletedAt WHERE r.id = :id")
    void deleteById(String id, Instant deletedAt);

}
