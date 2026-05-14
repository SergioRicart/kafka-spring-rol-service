package com.sergioricart.role_service.fixtures;

import com.sergioricart.role_service.role.application.http.role.created.CreateRoleCommand;
import com.sergioricart.role_service.role.application.http.role.delete.DeleteRoleCommand;
import com.sergioricart.role_service.role.application.http.role.update.UpdateRoleCommand;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.infrastructure.api.dto.request.RoleCreatedRequest;
import com.sergioricart.role_service.role.infrastructure.api.dto.request.RoleUpdatedRequest;

import java.time.Instant;
import java.util.List;

public final class RoleFixture {

    private RoleFixture() {}

    // ── Valores base ──────────────────────────────────────────────────────────
    public static final String ROLE_ID          = "role-123";
    public static final String ROLE_NAME        = "Administrador";
    public static final String ROLE_DESCRIPTION = "Rol con acceso total";
    public static final String PAGE_ID_1        = "page-1";
    public static final String PAGE_ID_2        = "page-2";

    public static final String UPDATED_NAME        = "Supervisor";
    public static final String UPDATED_DESCRIPTION = "Rol con acceso parcial";
    public static final String UPDATED_PAGE_ID     = "page-3";

    public static final String UNKNOWN_ID = "no-existe";

    // ── Rutas API ─────────────────────────────────────────────────────────────
    public static final String CREATE_ROLE_PATH    = "/api/v1/role/create";
    public static final String GET_ALL_ROLES_PATH  = "/api/v1/role";
    public static final String GET_ROLE_BY_ID_PATH = "/api/v1/role/";
    public static final String UPDATE_ROLE_PATH    = "/api/v1/role/";
    public static final String DELETE_ROLE_PATH    = "/api/v1/role/";
    public static final String GET_PAGES_BY_ROLE_PATH = "/api/v1/page/role/";

    // ── Objetos de dominio ────────────────────────────────────────────────────

    public static Page aPage() {
        return Page.builder()
                .id(PAGE_ID_1)
                .name("Dashboard")
                .description("Página principal")
                .url("/dashboard")
                .build();
    }

    public static List<Page> somePages() {
        return List.of(
                Page.builder().id(PAGE_ID_1).name("Dashboard").description("Página principal").url("/dashboard").build(),
                Page.builder().id(PAGE_ID_2).name("Usuarios").description("Gestión de usuarios").url("/users").build()
        );
    }

    public static Role aRole() {
        return Role.builder()
                .id(ROLE_ID)
                .name(ROLE_NAME)
                .description(ROLE_DESCRIPTION)
                .pages(somePages())
                .createdAt(Instant.now())
                .build();
    }

    // ── Commands ──────────────────────────────────────────────────────────────

    public static CreateRoleCommand aCreateRoleCommand() {
        CreateRoleCommand command = new CreateRoleCommand();
        command.setName(ROLE_NAME);
        command.setDescription(ROLE_DESCRIPTION);
        command.setPagesId(List.of(PAGE_ID_1, PAGE_ID_2));
        return command;
    }

    public static DeleteRoleCommand aDeleteRoleCommand() {
        return new DeleteRoleCommand(ROLE_ID);
    }

    public static UpdateRoleCommand aFullUpdateRoleCommand() {
        UpdateRoleCommand command = new UpdateRoleCommand();
        command.setId(ROLE_ID);
        command.setName(UPDATED_NAME);
        command.setDescription(UPDATED_DESCRIPTION);
        command.setPagesId(List.of(UPDATED_PAGE_ID));
        return command;
    }

    public static UpdateRoleCommand aPartialUpdateRoleCommand() {
        UpdateRoleCommand command = new UpdateRoleCommand();
        command.setId(ROLE_ID);
        command.setName(UPDATED_NAME);
        return command;
    }

    public static UpdateRoleCommand aNoOpUpdateRoleCommand() {
        UpdateRoleCommand command = new UpdateRoleCommand();
        command.setId(ROLE_ID);
        command.setName(ROLE_NAME);
        command.setDescription(ROLE_DESCRIPTION);
        return command;
    }

    // ── DTOs HTTP ─────────────────────────────────────────────────────────────

    public static RoleCreatedRequest aRoleCreatedRequest() {
        RoleCreatedRequest request = new RoleCreatedRequest();
        request.setName(ROLE_NAME);
        request.setDescription(ROLE_DESCRIPTION);
        request.setIdPages(List.of(PAGE_ID_1, PAGE_ID_2));
        return request;
    }

    public static RoleUpdatedRequest aPartialRoleUpdatedRequest() {
        RoleUpdatedRequest request = new RoleUpdatedRequest();
        request.setName(UPDATED_NAME);
        return request;
    }
}
