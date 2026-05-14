package com.sergioricart.role_service.role.infrastructure.api.contoller;


import com.sergioricart.commons.application.Mediator;
import com.sergioricart.role_service.role.application.http.created.CreateRoleCommand;
import com.sergioricart.role_service.role.infrastructure.api.dto.request.RoleCreatedRequest;
import com.sergioricart.role_service.role.infrastructure.api.mapper.RoleApiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
