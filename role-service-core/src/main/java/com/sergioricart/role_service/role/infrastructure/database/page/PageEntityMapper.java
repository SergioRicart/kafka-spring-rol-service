package com.sergioricart.role_service.role.infrastructure.database.page;

import com.sergioricart.role_service.role.domain.entity.Page;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PageEntityMapper {

    PageEntity mapToPageEntity(Page page);

    Page mapToPage(PageEntity pageEntity);
}
