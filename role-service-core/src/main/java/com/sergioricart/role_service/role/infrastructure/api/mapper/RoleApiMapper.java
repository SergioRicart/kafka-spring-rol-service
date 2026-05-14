package com.sergioricart.role_service.role.infrastructure.api.mapper;

import com.sergioricart.role_service.role.application.http.role.created.CreateRoleCommand;
import com.sergioricart.role_service.role.application.http.role.findById.GetRolesByIdQuery;
import com.sergioricart.role_service.role.application.http.role.update.UpdateRoleCommand;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.infrastructure.api.dto.request.RoleCreatedRequest;
import com.sergioricart.role_service.role.infrastructure.api.dto.request.RoleUpdatedRequest;
import com.sergioricart.role_service.role.infrastructure.api.dto.response.PageResponse;
import com.sergioricart.role_service.role.infrastructure.api.dto.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleApiMapper {

    @Mapping(source = "idPages", target = "pagesId")
    CreateRoleCommand mapToCreateRoleCommand(RoleCreatedRequest request);

    UpdateRoleCommand mapToUpdateRoleCommand(RoleUpdatedRequest request);

    RoleResponse mapToRoleResponse(Role role);

    List<RoleResponse> mapToRoleResponseList(List<Role> roles);

    PageResponse mapToPageResponse(Page page);

    List<PageResponse> mapToPageResponseList(List<Page> pages);

}
