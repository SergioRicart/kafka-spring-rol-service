package com.sergioricart.role_service.role.application.http.role.findAll;

import com.sergioricart.commons.application.CommandHandler;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetAllRolesHandler implements CommandHandler<GetAllRolesQuery, List<Role>> {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Role> handle(GetAllRolesQuery query) {
        log.info("Getting all roles");
        return roleRepository.findAll();
    }

    @Override
    public Class<GetAllRolesQuery> getCommandType() {
        return GetAllRolesQuery.class;
    }
}