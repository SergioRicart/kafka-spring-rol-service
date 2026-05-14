package com.sergioricart.role_service.role.application.http.role.update;

import com.sergioricart.commons.application.CommandHandler;
import com.sergioricart.commons.application.VoidResponse;
import com.sergioricart.role_service.role.domain.constant.RoleConstants;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.event.RoleUpdatedDomainEvent;
import com.sergioricart.role_service.role.domain.exception.RoleNotFonundException;
import com.sergioricart.role_service.role.domain.port.PageRepository;
import com.sergioricart.role_service.role.domain.port.RoleEvent;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

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

        log.info("Updating role: {}", command.getId());

        Role existingRole = roleRepository.findById(command.getId())
                .orElseThrow(() ->
                        new RoleNotFonundException(RoleConstants.ROLE_NOT_FOUND_BY_ID_MESSAGE, command.getId())
                );

        List<Page> pages = command.getPagesId() != null
                ? pageRepository.findAllByIds(command.getPagesId())
                : existingRole.getPages();

        Role updatedRole = Role.builder()
                .id(existingRole.getId())
                .name(command.getName() != null ? command.getName() : existingRole.getName())
                .description(command.getDescription() != null ? command.getDescription() : existingRole.getDescription())
                .pages(pages)
                .createdAt(existingRole.getCreatedAt())
                .updatedAt(Instant.now())
                .build();

        roleRepository.save(updatedRole);

        roleEvent.sendRoleUpdatedEvent(RoleUpdatedDomainEvent.of(updatedRole));

        return new VoidResponse();
    }

    @Override
    public Class<UpdateRoleCommand> getCommandType() {
        return UpdateRoleCommand.class;
    }
}
