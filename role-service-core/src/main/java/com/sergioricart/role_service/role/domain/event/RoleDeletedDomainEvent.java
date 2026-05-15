package com.sergioricart.role_service.role.domain.event;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class RoleDeletedDomainEvent {

    private String id;

    private Instant deletedAt;

    public static RoleDeletedDomainEvent of(String id) {
        return RoleDeletedDomainEvent.builder()
                .id(id)
                .deletedAt(Instant.now())
                .build();
    }
}
