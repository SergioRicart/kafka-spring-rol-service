package com.sergioricart.role_service.role.application.http.role.findAll;

import com.sergioricart.role_service.fixtures.RoleFixture;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllRolesHandlerTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private GetAllRolesHandler handler;

    @Test
    void handle_returnsAllRolesFromRepository() {
        List<Role> roles = List.of(RoleFixture.aRole());
        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> result = handler.handle(new GetAllRolesQuery());

        assertThat(result).isEqualTo(roles);
    }

    @Test
    void handle_whenNoRolesExist_returnsEmptyList() {
        when(roleRepository.findAll()).thenReturn(List.of());

        List<Role> result = handler.handle(new GetAllRolesQuery());

        assertThat(result).isEmpty();
    }

    @Test
    void handle_delegatesToRepository() {
        when(roleRepository.findAll()).thenReturn(List.of());

        handler.handle(new GetAllRolesQuery());

        verify(roleRepository).findAll();
    }

    @Test
    void getCommandType_returnsGetAllRolesQueryClass() {
        assertThat(handler.getCommandType()).isEqualTo(GetAllRolesQuery.class);
    }
}
