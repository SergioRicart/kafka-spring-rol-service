package com.sergioricart.role_service.role.domain.event;

import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class RoleUpdatedDomainEvent {

    private String id;

    private String name;

    private String description;

    private List<Page> pages;

    private Instant updatedAt;

    public static RoleUpdatedDomainEvent of(Role role) {
        return RoleUpdatedDomainEvent.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .pages(role.getPages())
                .updatedAt(role.getUpdatedAt())
                .build();
    }
}
