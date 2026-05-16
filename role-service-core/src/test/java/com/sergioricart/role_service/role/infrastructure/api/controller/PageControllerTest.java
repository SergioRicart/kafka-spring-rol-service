package com.sergioricart.role_service.role.infrastructure.api.controller;

import com.sergioricart.commons.application.Mediator;
import com.sergioricart.role_service.fixtures.RoleFixture;
import com.sergioricart.role_service.role.infrastructure.api.contoller.PageController;
import com.sergioricart.role_service.role.infrastructure.api.dto.response.PageResponseBase;
import com.sergioricart.role_service.role.infrastructure.api.mapper.RoleApiMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PageController.class)
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Mediator mediator;

    @MockitoBean
    private RoleApiMapper roleApiMapper;

    // ──────────────── GET /api/v1/page/role/{roleId} ────────────────

    @Test
    void getPagesByRole_givenExistingRoleId_returns200WithPages() throws Exception {
        PageResponseBase pageResponse = PageResponseBase.builder()
                .id(RoleFixture.PAGE_ID_1)
                .name("Dashboard")
                .url("/dashboard")
                .build();
        when(mediator.dispatch(any())).thenReturn(RoleFixture.somePages());
        when(roleApiMapper.mapToPageResponseList(any())).thenReturn(List.of(pageResponse));

        mockMvc.perform(get(RoleFixture.GET_PAGES_BY_ROLE_PATH + RoleFixture.ROLE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(RoleFixture.PAGE_ID_1));
    }

    @Test
    void getPagesByRole_givenNonExistingRoleId_returns200WithEmptyList() throws Exception {
        when(mediator.dispatch(any())).thenReturn(List.of());
        when(roleApiMapper.mapToPageResponseList(any())).thenReturn(List.of());

        mockMvc.perform(get(RoleFixture.GET_PAGES_BY_ROLE_PATH + RoleFixture.UNKNOWN_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
