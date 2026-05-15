package com.sergioricart.role_service.role.application.http.role.created;

import com.sergioricart.commons.application.Command;
import com.sergioricart.commons.application.VoidResponse;
import com.sergioricart.role_service.role.domain.entity.Page;
import lombok.Data;

import java.util.List;

@Data
public class CreateRoleCommand implements Command<VoidResponse> {

    private String name;

    private String description;

    private List<String> pagesId;

}
