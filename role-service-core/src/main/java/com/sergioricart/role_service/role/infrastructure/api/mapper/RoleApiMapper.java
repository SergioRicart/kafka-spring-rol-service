package com.sergioricart.role_service.role.infrastructure.api.mapper;

import com.sergioricart.role_service.role.application.http.role.created.CreateRoleCommand;
import com.sergioricart.role_service.role.application.http.role.update.UpdateRoleCommand;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.infrastructure.api.dto.request.RoleRequestBase;
import com.sergioricart.role_service.role.infrastructure.api.dto.response.PageResponseBase;
import com.sergioricart.role_service.role.infrastructure.api.dto.response.RoleResponseBase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleApiMapper {

    @Mapping(source = "idPages", target = "pagesId")
    CreateRoleCommand mapToCreateRoleCommand(RoleRequestBase request);

    @Mapping(source = "idPages", target = "pagesId")
    UpdateRoleCommand mapToUpdateRoleCommand(RoleRequestBase request);

    RoleResponseBase mapToRoleResponse(Role role);

    List<RoleResponseBase> mapToRoleResponseList(List<Role> roles);

    PageResponseBase mapToPageResponse(Page page);

    List<PageResponseBase> mapToPageResponseList(List<Page> pages);

}
