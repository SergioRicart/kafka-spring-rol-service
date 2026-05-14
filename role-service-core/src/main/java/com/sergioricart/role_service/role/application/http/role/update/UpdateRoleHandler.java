package com.sergioricart.role_service.role.application.http.role.update;

import com.sergioricart.commons.application.CommandHandler;
import com.sergioricart.commons.application.VoidResponse;
import com.sergioricart.role_service.role.application.http.role.created.CreateRoleCommand;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.event.RoleCreatedDomainEvent;
import com.sergioricart.role_service.role.domain.port.PageRepository;
import com.sergioricart.role_service.role.domain.port.RoleEvent;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateRoleHandler implements CommandHandler<UpdateRoleCommand, VoidResponse> {

    private final RoleRepository roleRepository;

    private final PageRepository pageRepository;

    private final RoleEvent roleEvent;

    @Override
    @Transactional
    public VoidResponse handle(UpdateRoleCommand command) {


        return new VoidResponse();
    }

    @Override
    public Class<UpdateRoleCommand> getCommandType() {
        return UpdateRoleCommand.class;
    }
}
