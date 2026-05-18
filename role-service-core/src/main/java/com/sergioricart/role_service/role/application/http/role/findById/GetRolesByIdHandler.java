package com.sergioricart.role_service.role.application.http.role.findById;

import com.sergioricart.commons.application.CommandHandler;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetRolesByIdHandler implements CommandHandler<GetRolesByIdQuery, Optional<Role>> {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> handle(GetRolesByIdQuery query) {

        log.info("GetRolesByIdQuery:{}", query);

        return roleRepository.findById(query.getId());

    }

    @Override
    public Class<GetRolesByIdQuery> getCommandType() {
        return GetRolesByIdQuery.class;
    }
}