package com.sergioricart.role_service.role.infrastructure.api.mapper;

import com.sergioricart.role_service.role.application.http.created.CreateRoleCommand;
import com.sergioricart.role_service.role.application.http.update.UpdateRoleCommand;
import com.sergioricart.role_service.role.infrastructure.api.dto.request.RoleCreatedRequest;
import com.sergioricart.role_service.role.infrastructure.api.dto.request.RoleUpdatedRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleApiMapper {

    @Mapping(source = "idPages", target = "pagesId")
    CreateRoleCommand mapToCreateRoleCommand(RoleCreatedRequest request);

    UpdateRoleCommand mapToUpdateRoleCommand(RoleUpdatedRequest request);

}
