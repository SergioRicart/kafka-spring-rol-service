package com.sergioricart.role_service.role.domain.event;


import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class RoleCreatedDomainEvent {

    private String id;

    private String name;

    private String description;

    private List<Page> pages;

    private Instant createdAt;

    public static RoleCreatedDomainEvent of(Role user) {
        return RoleCreatedDomainEvent.builder()
                .id(user.getId())
                .name(user.getName())
                .description(user.getDescription())
                .pages(user.getPages())
                .createdAt(Instant.now())
                .build();
    }
}
