package com.sergioricart.role_service.role.application.http.page.findByRole;

import com.sergioricart.role_service.fixtures.RoleFixture;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPagesByRoleHandlerTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private GetPagesByRoleHandler handler;

    @Test
    void handle_givenExistingRole_returnsPagesOfThatRole() {
        Role role = RoleFixture.aRole();
        when(roleRepository.findById(RoleFixture.ROLE_ID)).thenReturn(Optional.of(role));

        List<Page> result = handler.handle(new GetPagesByRoleQuery(RoleFixture.ROLE_ID));

        assertThat(result).isEqualTo(role.getPages());
    }

    @Test
    void handle_givenNonExistingRole_returnsEmptyList() {
        when(roleRepository.findById(RoleFixture.UNKNOWN_ID)).thenReturn(Optional.empty());

        List<Page> result = handler.handle(new GetPagesByRoleQuery(RoleFixture.UNKNOWN_ID));

        assertThat(result).isEmpty();
    }

    @Test
    void handle_givenRoleWithNoPages_returnsEmptyList() {
        Role rolSinPages = Role.builder()
                .id(RoleFixture.ROLE_ID)
                .name(RoleFixture.ROLE_NAME)
                .pages(List.of())
                .build();
        when(roleRepository.findById(RoleFixture.ROLE_ID)).thenReturn(Optional.of(rolSinPages));

        List<Page> result = handler.handle(new GetPagesByRoleQuery(RoleFixture.ROLE_ID));

        assertThat(result).isEmpty();
    }

    @Test
    void getCommandType_returnsGetPagesByRoleQueryClass() {
        assertThat(handler.getCommandType()).isEqualTo(GetPagesByRoleQuery.class);
    }
}
