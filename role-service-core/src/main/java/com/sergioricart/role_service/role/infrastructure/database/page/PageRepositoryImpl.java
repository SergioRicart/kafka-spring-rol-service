package com.sergioricart.role_service.role.infrastructure.database.page;

import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.port.PageRepository;
import com.sergioricart.role_service.role.infrastructure.database.role.RoleEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PageRepositoryImpl implements PageRepository {

    private final PageRepositoryData pageRepositoryData;

    private final PageEntityMapper roleEntityMapper;

    @Override
    public List<Page> findAllByIds(List<String> ids) {
        return pageRepositoryData.findAllByIdIn(ids)
                .stream()
                .map(roleEntityMapper::mapToPage)
                .toList();
    }

    @Override
    public List<Page> findAll() {
        return pageRepositoryData.findAll()
                .stream()
                .map(roleEntityMapper::mapToPage)
                .toList();
    }

}
