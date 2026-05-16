package com.sergioricart.role_service.role.infrastructure.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergioricart.commons.application.Mediator;
import com.sergioricart.commons.application.VoidResponse;
import com.sergioricart.role_service.fixtures.RoleFixture;
import com.sergioricart.role_service.role.application.http.role.delete.DeleteRoleCommand;
import com.sergioricart.role_service.role.application.http.role.update.UpdateRoleCommand;
import com.sergioricart.role_service.role.domain.exception.RoleNotFonundException;
import com.sergioricart.role_service.role.infrastructure.api.contoller.RoleController;
import com.sergioricart.role_service.role.infrastructure.api.dto.response.RoleResponseBase;
import com.sergioricart.role_service.role.infrastructure.api.mapper.RoleApiMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Mediator mediator;

    @MockitoBean
    private RoleApiMapper roleApiMapper;

    @Autowired
    private ObjectMapper objectMapper;

    // ──────────────── POST /api/v1/role/create ────────────────

    @Test
    void createRole_givenValidRequest_returns201() throws Exception {
        when(roleApiMapper.mapToCreateRoleCommand(any())).thenReturn(RoleFixture.aCreateRoleCommand());
        when(mediator.dispatch(any())).thenReturn(new VoidResponse());

        mockMvc.perform(post(RoleFixture.CREATE_ROLE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RoleFixture.aRoleCreatedRequest())))
                .andExpect(status().isCreated());
    }

    // ──────────────── GET /api/v1/role ────────────────

    @Test
    void getAllRoles_returns200WithList() throws Exception {
        RoleResponseBase response = RoleResponseBase.builder()
                .id(RoleFixture.ROLE_ID)
                .name(RoleFixture.ROLE_NAME)
                .description(RoleFixture.ROLE_DESCRIPTION)
                .build();
        when(mediator.dispatch(any())).thenReturn(List.of(RoleFixture.aRole()));
        when(roleApiMapper.mapToRoleResponseList(any())).thenReturn(List.of(response));

        mockMvc.perform(get(RoleFixture.GET_ALL_ROLES_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(RoleFixture.ROLE_ID));
    }

    @Test
    void getAllRoles_whenNoRoles_returns200WithEmptyList() throws Exception {
        when(mediator.dispatch(any())).thenReturn(List.of());
        when(roleApiMapper.mapToRoleResponseList(any())).thenReturn(List.of());

        mockMvc.perform(get(RoleFixture.GET_ALL_ROLES_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ──────────────── GET /api/v1/role/{id} ────────────────

    @Test
    void getRoleById_givenExistingId_returns200() throws Exception {
        RoleResponseBase response = RoleResponseBase.builder()
                .id(RoleFixture.ROLE_ID)
                .name(RoleFixture.ROLE_NAME)
                .build();
        when(mediator.dispatch(any())).thenReturn(Optional.of(RoleFixture.aRole()));
        when(roleApiMapper.mapToRoleResponse(any())).thenReturn(response);

        mockMvc.perform(get(RoleFixture.GET_ROLE_BY_ID_PATH + RoleFixture.ROLE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(RoleFixture.ROLE_ID));
    }

    @Test
    void getRoleById_givenNonExistingId_propagatesRoleNotFonundException() {
        when(mediator.dispatch(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                mockMvc.perform(get(RoleFixture.GET_ROLE_BY_ID_PATH + RoleFixture.UNKNOWN_ID)))
                .hasCauseInstanceOf(RoleNotFonundException.class);
    }

    // ──────────────── PATCH /api/v1/role/{id} ────────────────

    @Test
    void updateRole_givenValidRequest_returns200() throws Exception {
        when(roleApiMapper.mapToUpdateRoleCommand(any())).thenReturn(RoleFixture.aPartialUpdateRoleCommand());
        when(mediator.dispatch(any(UpdateRoleCommand.class))).thenReturn(new VoidResponse());

        mockMvc.perform(patch(RoleFixture.UPDATE_ROLE_PATH + RoleFixture.ROLE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RoleFixture.aPartialRoleUpdatedRequest())))
                .andExpect(status().isOk());
    }

    @Test
    void updateRole_givenNonExistingRole_propagatesRoleNotFonundException() {
        when(roleApiMapper.mapToUpdateRoleCommand(any())).thenReturn(RoleFixture.aPartialUpdateRoleCommand());
        when(mediator.dispatch(any(UpdateRoleCommand.class)))
                .thenThrow(new RoleNotFonundException("Role not found"));

        assertThatThrownBy(() ->
                mockMvc.perform(patch(RoleFixture.UPDATE_ROLE_PATH + RoleFixture.UNKNOWN_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RoleFixture.aPartialRoleUpdatedRequest()))))
                .hasCauseInstanceOf(RoleNotFonundException.class);
    }

    // ──────────────── DELETE /api/v1/role/{id} ────────────────

    @Test
    void deleteRole_givenValidId_returns204() throws Exception {
        when(mediator.dispatch(any(DeleteRoleCommand.class))).thenReturn(new VoidResponse());

        mockMvc.perform(delete(RoleFixture.DELETE_ROLE_PATH + RoleFixture.ROLE_ID))
                .andExpect(status().isNoContent());

        verify(mediator).dispatch(any(DeleteRoleCommand.class));
    }

    @Test
    void deleteRole_givenNonExistingRole_propagatesRoleNotFonundException() {
        when(mediator.dispatch(any(DeleteRoleCommand.class)))
                .thenThrow(new RoleNotFonundException("Role not found"));

        assertThatThrownBy(() ->
                mockMvc.perform(delete(RoleFixture.DELETE_ROLE_PATH + RoleFixture.UNKNOWN_ID)))
                .hasCauseInstanceOf(RoleNotFonundException.class);
    }
}
