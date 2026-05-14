package com.sergioricart.role_service.role.application.http.role.delete;

import com.sergioricart.commons.application.CommandHandler;
import com.sergioricart.commons.application.VoidResponse;
import com.sergioricart.role_service.role.domain.constant.RoleConstants;
import com.sergioricart.role_service.role.domain.event.RoleDeletedDomainEvent;
import com.sergioricart.role_service.role.domain.exception.RoleNotFonundException;
import com.sergioricart.role_service.role.domain.port.RoleEvent;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteRoleHandler implements CommandHandler<DeleteRoleCommand, VoidResponse> {

    private final RoleRepository roleRepository;

    private final RoleEvent roleEvent;

    @Override
    @Transactional
    public VoidResponse handle(DeleteRoleCommand command) {

        log.info("Deleting role: {}", command.getId());

        roleRepository.findById(command.getId())
                .orElseThrow(() ->
                        new RoleNotFonundException(RoleConstants.ROLE_NOT_FOUND_BY_ID_MESSAGE, command.getId()
                )
        );

        roleRepository.deleteById(command.getId());

        roleEvent.sendRoleDeletedEvent(RoleDeletedDomainEvent.of(command.getId()));

        return new VoidResponse();
    }

    @Override
    public Class<DeleteRoleCommand> getCommandType() {
        return DeleteRoleCommand.class;
    }
}
