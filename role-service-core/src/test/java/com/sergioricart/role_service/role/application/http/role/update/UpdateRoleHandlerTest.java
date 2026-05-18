package com.sergioricart.role_service.role.application.http.role.update;

import com.sergioricart.role_service.fixtures.RoleFixture;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.event.RoleUpdatedDomainEvent;
import com.sergioricart.role_service.role.domain.exception.RoleNotFonundException;
import com.sergioricart.role_service.role.domain.port.PageRepository;
import com.sergioricart.role_service.role.domain.port.RoleEvent;
import com.sergioricart.role_service.role.domain.port.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRoleHandlerTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PageRepository pageRepository;

    @Mock
    private RoleEvent roleEvent;

    @InjectMocks
    private UpdateRoleHandler handler;

    @Test
    void handle_givenFullUpdate_updatesAllFields() {
        Role role = RoleFixture.aRole();
        when(roleRepository.findById(RoleFixture.ROLE_ID)).thenReturn(Optional.of(role));
        Page updatedPage = Page.builder().id(RoleFixture.UPDATED_PAGE_ID).name("Reportes").description("Reportes").url("/reports").build();
        when(pageRepository.findAllByIds(List.of(RoleFixture.UPDATED_PAGE_ID))).thenReturn(List.of(updatedPage));

        handler.handle(RoleFixture.aFullUpdateRoleCommand());

        ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(roleCaptor.capture());

        Role rolActualizado = roleCaptor.getValue();
        assertThat(rolActualizado.getName()).isEqualTo(RoleFixture.UPDATED_NAME);
        assertThat(rolActualizado.getDescription()).isEqualTo(RoleFixture.UPDATED_DESCRIPTION);
        assertThat(rolActualizado.getPages()).containsExactly(updatedPage);
        assertThat(rolActualizado.getUpdatedAt()).isNotNull();
        assertThat(rolActualizado.getCreatedAt()).isEqualTo(role.getCreatedAt());
    }

    @Test
    void handle_givenPartialUpdate_keepsUnchangedFields() {
        Role role = RoleFixture.aRole();
        when(roleRepository.findById(RoleFixture.ROLE_ID)).thenReturn(Optional.of(role));

        handler.handle(RoleFixture.aPartialUpdateRoleCommand());

        ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(roleCaptor.capture());

        Role rolActualizado = roleCaptor.getValue();
        assertThat(rolActualizado.getName()).isEqualTo(RoleFixture.UPDATED_NAME);
        assertThat(rolActualizado.getDescription()).isEqualTo(RoleFixture.ROLE_DESCRIPTION);
        assertThat(rolActualizado.getPages()).isEqualTo(role.getPages());
    }

    @Test
    void handle_givenValidUpdate_publishesRoleUpdatedEvent() {
        when(roleRepository.findById(RoleFixture.ROLE_ID)).thenReturn(Optional.of(RoleFixture.aRole()));

        handler.handle(RoleFixture.aPartialUpdateRoleCommand());

        verify(roleEvent).sendRoleUpdatedEvent(any(RoleUpdatedDomainEvent.class));
        verifyNoMoreInteractions(roleEvent);
    }

    @Test
    void handle_givenValidUpdate_savesUpdatedRole() {
        when(roleRepository.findById(RoleFixture.ROLE_ID)).thenReturn(Optional.of(RoleFixture.aRole()));

        handler.handle(RoleFixture.aPartialUpdateRoleCommand());

        verify(roleRepository).save(any(Role.class));
        verify(roleRepository, never()).deleteById(any());
    }

    @Test
    void handle_givenNonExistingRole_throwsRoleNotFonundException() {
        when(roleRepository.findById(RoleFixture.UNKNOWN_ID)).thenReturn(Optional.empty());

        UpdateRoleCommand command = new UpdateRoleCommand();
        command.setId(RoleFixture.UNKNOWN_ID);

        assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(RoleNotFonundException.class);

        verifyNoInteractions(roleEvent);
        verify(roleRepository, never()).save(any());
    }

    @Test
    void getCommandType_returnsUpdateRoleCommandClass() {
        assertThat(handler.getCommandType()).isEqualTo(UpdateRoleCommand.class);
    }
}
