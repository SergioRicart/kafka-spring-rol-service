package com.sergioricart.role_service.role.infrastructure.database.page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepositoryData extends JpaRepository<PageEntity, String> {

    List<PageEntity> findAllByIdIn(List<String> ids);

}
