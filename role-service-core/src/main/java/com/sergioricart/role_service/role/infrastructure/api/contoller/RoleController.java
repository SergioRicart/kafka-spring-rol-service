package com.sergioricart.role_service.role.infrastructure.api.contoller;


import com.sergioricart.commons.application.Mediator;
import com.sergioricart.role_service.role.application.http.role.created.CreateRoleCommand;
import com.sergioricart.role_service.role.application.http.role.delete.DeleteRoleCommand;
import com.sergioricart.role_service.role.application.http.role.findAll.GetAllRolesQuery;
import com.sergioricart.role_service.role.application.http.role.findById.GetRolesByIdQuery;
import com.sergioricart.role_service.role.application.http.role.update.UpdateRoleCommand;
import com.sergioricart.role_service.role.domain.constant.RoleConstants;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.exception.RoleNotFonundException;
import com.sergioricart.role_service.role.infrastructure.api.dto.request.RoleRequestBase;
import com.sergioricart.role_service.role.infrastructure.api.dto.response.RoleResponseBase;
import com.sergioricart.role_service.role.infrastructure.api.mapper.RoleApiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody RoleRequestBase roleDto) {

        log.info("Creating role: {}", roleDto);

        CreateRoleCommand command = apiMapper.mapToCreateRoleCommand(roleDto);

        mediator.dispatch(command);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping
    public ResponseEntity<List<RoleResponseBase>> getAllRoles() {

        log.info("Getting all roles");

        List<Role> roles = mediator.dispatch(new GetAllRolesQuery());

        return ResponseEntity.ok(apiMapper.mapToRoleResponseList(roles));

    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseBase> getRoleById(@PathVariable String id) {

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

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable String id, @RequestBody RoleRequestBase request) {

        log.info("Updating role: {}", id);

        UpdateRoleCommand command = apiMapper.mapToUpdateRoleCommand(request);
        command.setId(id);

        mediator.dispatch(command);

        return ResponseEntity.ok().build();

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable String id) {

        log.info("Deleting role: {}", id);

        mediator.dispatch(new DeleteRoleCommand(id));

        return ResponseEntity.noContent().build();

    }

}
