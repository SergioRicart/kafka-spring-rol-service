package com.sergioricart.role_service.role.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class Role {

    private String id;

    private String name;

    private String description;

    private List<Page> pages;

    private Instant createdAt;

    private Instant updatedAt;

}
