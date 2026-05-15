package com.sergioricart.role_service.role.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Page {

    private String id;

    private String name;

    private String description;

    private String url;
}
