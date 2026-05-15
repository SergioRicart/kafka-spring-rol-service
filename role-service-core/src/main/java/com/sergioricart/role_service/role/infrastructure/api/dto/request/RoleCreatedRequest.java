package com.sergioricart.role_service.role.infrastructure.api.dto.request;

import com.sergioricart.role_service.role.domain.entity.Page;

import java.util.List;

import lombok.Data;

@Data
public class RoleCreatedRequest {

    private String name;

    private String description;

    private List<String> idPages;

}
