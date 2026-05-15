package com.sergioricart.role_service.role.application.http.page.findByRole;

import com.sergioricart.commons.application.CommandHandler;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetPagesByRoleHandler implements CommandHandler<GetPagesByRoleQuery, List<Page>> {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Page> handle(GetPagesByRoleQuery query) {

        log.info("Getting pages for role: {}", query.getRoleId());

        return roleRepository.findById(query.getRoleId())
                .map(role -> role.getPages())
                .orElse(List.of());
    }

    @Override
    public Class<GetPagesByRoleQuery> getCommandType() {
        return GetPagesByRoleQuery.class;
    }
}
