package com.sergioricart.role_service.role.domain.port;

import com.sergioricart.role_service.role.domain.event.RoleCreatedDomainEvent;

public interface RoleEvent {

    void sendRoleCreatedEvent(RoleCreatedDomainEvent event);
}
