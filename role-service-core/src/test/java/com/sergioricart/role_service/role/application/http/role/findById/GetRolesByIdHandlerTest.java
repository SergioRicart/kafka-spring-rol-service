package com.sergioricart.role_service.role.application.http.role.findById;

import com.sergioricart.role_service.fixtures.RoleFixture;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRolesByIdHandlerTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private GetRolesByIdHandler handler;

    @Test
    void handle_givenExistingId_returnsRole() {
        Role role = RoleFixture.aRole();
        when(roleRepository.findById(RoleFixture.ROLE_ID)).thenReturn(Optional.of(role));

        Optional<Role> result = handler.handle(GetRolesByIdQuery.builder().id(RoleFixture.ROLE_ID).build());

        assertThat(result).isPresent().contains(role);
    }

    @Test
    void handle_givenNonExistingId_returnsEmpty() {
        when(roleRepository.findById(RoleFixture.UNKNOWN_ID)).thenReturn(Optional.empty());

        Optional<Role> result = handler.handle(GetRolesByIdQuery.builder().id(RoleFixture.UNKNOWN_ID).build());

        assertThat(result).isEmpty();
    }

    @Test
    void handle_delegatesToRepositoryWithCorrectId() {
        when(roleRepository.findById(RoleFixture.ROLE_ID)).thenReturn(Optional.of(RoleFixture.aRole()));

        handler.handle(GetRolesByIdQuery.builder().id(RoleFixture.ROLE_ID).build());

        verify(roleRepository).findById(RoleFixture.ROLE_ID);
    }

    @Test
    void getCommandType_returnsGetRolesByIdQueryClass() {
        assertThat(handler.getCommandType()).isEqualTo(GetRolesByIdQuery.class);
    }
}
