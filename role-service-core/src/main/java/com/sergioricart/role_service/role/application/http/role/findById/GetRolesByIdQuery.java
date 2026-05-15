package com.sergioricart.role_service.role.application.http.role.findById;

import com.sergioricart.commons.application.Command;
import com.sergioricart.role_service.role.domain.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Builder
@Data
public class GetRolesByIdQuery implements Command<Optional<Role>> {

    private String id;
}