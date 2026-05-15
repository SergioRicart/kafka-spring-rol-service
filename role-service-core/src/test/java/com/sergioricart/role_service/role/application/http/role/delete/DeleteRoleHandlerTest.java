package com.sergioricart.role_service.role.application.http.role.delete;

import com.sergioricart.role_service.fixtures.RoleFixture;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.event.RoleDeletedDomainEvent;
import com.sergioricart.role_service.role.domain.exception.RoleNotFonundException;
import com.sergioricart.role_service.role.domain.port.RoleEvent;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRoleHandlerTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleEvent roleEvent;

    @InjectMocks
    private DeleteRoleHandler handler;

    @Test
    void handle_givenExistingRole_deletesAndPublishesEvent() {
        Role role = RoleFixture.aRole();
        when(roleRepository.findById(RoleFixture.ROLE_ID)).thenReturn(Optional.of(role));

        handler.handle(RoleFixture.aDeleteRoleCommand());

        verify(roleRepository).deleteById(RoleFixture.ROLE_ID);
        verify(roleEvent).sendRoleDeletedEvent(any(RoleDeletedDomainEvent.class));
    }

    @Test
    void handle_givenExistingRole_returnsVoidResponse() {
        when(roleRepository.findById(RoleFixture.ROLE_ID)).thenReturn(Optional.of(RoleFixture.aRole()));

        var result = handler.handle(RoleFixture.aDeleteRoleCommand());

        assertThat(result).isNotNull();
    }

    @Test
    void handle_givenNonExistingRole_throwsRoleNotFonundException() {
        when(roleRepository.findById(RoleFixture.UNKNOWN_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(new DeleteRoleCommand(RoleFixture.UNKNOWN_ID)))
                .isInstanceOf(RoleNotFonundException.class);

        verifyNoInteractions(roleEvent);
    }

    @Test
    void handle_givenNonExistingRole_neverCallsDeleteById() {
        when(roleRepository.findById(RoleFixture.UNKNOWN_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(new DeleteRoleCommand(RoleFixture.UNKNOWN_ID)))
                .isInstanceOf(RoleNotFonundException.class);

        verify(roleRepository, never()).deleteById(any());
    }

    @Test
    void getCommandType_returnsDeleteRoleCommandClass() {
        assertThat(handler.getCommandType()).isEqualTo(DeleteRoleCommand.class);
    }
}
