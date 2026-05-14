package com.sergioricart.role_service.role.domain.port;

import com.sergioricart.role_service.role.domain.event.RoleCreatedDomainEvent;
import com.sergioricart.role_service.role.domain.event.RoleDeletedDomainEvent;
import com.sergioricart.role_service.role.domain.event.RoleUpdatedDomainEvent;

public interface RoleEvent {

    void sendRoleCreatedEvent(RoleCreatedDomainEvent event);

    void sendRoleUpdatedEvent(RoleUpdatedDomainEvent event);

    void sendRoleDeletedEvent(RoleDeletedDomainEvent event);
}
