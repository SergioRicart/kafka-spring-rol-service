package com.sergioricart.role_service.role.infrastructure.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageResponseBase {

    private String id;

    private String name;

    private String description;

    private String url;

}