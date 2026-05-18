package com.sergioricart.role_service.role.infrastructure.api.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleRequestBase {

    private String name;

    private String description;

    private List<String> idPages;
}
