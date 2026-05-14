package com.sergioricart.role_service.role.infrastructure.api.contoller;


import com.sergioricart.commons.application.Mediator;
import com.sergioricart.role_service.role.application.http.role.created.CreateRoleCommand;
import com.sergioricart.role_service.role.application.http.role.findAll.GetAllRolesQuery;
import com.sergioricart.role_service.role.application.http.role.findById.GetRolesByIdQuery;
import com.sergioricart.role_service.role.domain.constant.RoleConstants;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.exception.RoleNotFonundException;
import com.sergioricart.role_service.role.infrastructure.api.dto.request.RoleCreatedRequest;
import com.sergioricart.role_service.role.infrastructure.api.dto.response.RoleResponse;
import com.sergioricart.role_service.role.infrastructure.api.mapper.RoleApiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final Mediator mediator;

    private final RoleApiMapper apiMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody RoleCreatedRequest roleDto) {

        log.info("Creating role: {}", roleDto);

        CreateRoleCommand command = apiMapper.mapToCreateRoleCommand(roleDto);

        mediator.dispatch(command);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {

        log.info("Getting all roles");

        List<Role> roles = mediator.dispatch(new GetAllRolesQuery());

        return ResponseEntity.ok(apiMapper.mapToRoleResponseList(roles));

    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable String id) {

        log.info("Getting role by id: {}", id);

        Optional<Role> role = mediator.dispatch(
                GetRolesByIdQuery.builder()
                        .id(id)
                        .build()
        );

        return  ResponseEntity.ok(
                apiMapper.mapToRoleResponse(
                        role.orElseThrow( () ->
                            new RoleNotFonundException(RoleConstants.ROLE_NOT_FOUND_BY_ID_MESSAGE, id)
                        )
                )
        );

    }

}
