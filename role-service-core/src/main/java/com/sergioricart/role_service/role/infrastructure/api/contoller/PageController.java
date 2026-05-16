package com.sergioricart.role_service.role.infrastructure.api.contoller;

import com.sergioricart.commons.application.Mediator;
import com.sergioricart.role_service.role.application.http.page.findByRole.GetPagesByRoleQuery;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.infrastructure.api.dto.response.PageResponseBase;
import com.sergioricart.role_service.role.infrastructure.api.mapper.RoleApiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/page")
@RequiredArgsConstructor
public class PageController {

    private final Mediator mediator;

    private final RoleApiMapper apiMapper;

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<PageResponseBase>> getPagesByRole(@PathVariable String roleId) {

        log.info("Getting pages for role: {}", roleId);

        List<Page> pages = mediator.dispatch(new GetPagesByRoleQuery(roleId));

        return ResponseEntity.ok(apiMapper.mapToPageResponseList(pages));

    }

}
