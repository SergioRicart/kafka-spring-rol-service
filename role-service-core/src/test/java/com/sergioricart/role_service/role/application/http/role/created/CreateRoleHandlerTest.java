package com.sergioricart.role_service.role.application.http.role.created;

import com.sergioricart.commons.application.VoidResponse;
import com.sergioricart.role_service.fixtures.RoleFixture;
import com.sergioricart.role_service.role.domain.entity.Role;
import com.sergioricart.role_service.role.domain.event.RoleCreatedDomainEvent;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRoleHandlerTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PageRepository pageRepository;

    @Mock
    private RoleEvent roleEvent;

    @InjectMocks
    private CreateRoleHandler handler;

    @Test
    void handle_givenValidCommand_savesRoleWithCorrectData() {
        when(pageRepository.findAllByIds(any())).thenReturn(RoleFixture.somePages());

        handler.handle(RoleFixture.aCreateRoleCommand());

        ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(roleCaptor.capture());

        Role rolGuardado = roleCaptor.getValue();
        assertThat(rolGuardado.getName()).isEqualTo(RoleFixture.ROLE_NAME);
        assertThat(rolGuardado.getDescription()).isEqualTo(RoleFixture.ROLE_DESCRIPTION);
        assertThat(rolGuardado.getPages()).isEqualTo(RoleFixture.somePages());
        assertThat(rolGuardado.getId()).isNotNull();
        assertThat(rolGuardado.getCreatedAt()).isNotNull();
    }

    @Test
    void handle_givenValidCommand_publishesRoleCreatedEvent() {
        when(pageRepository.findAllByIds(any())).thenReturn(RoleFixture.somePages());

        handler.handle(RoleFixture.aCreateRoleCommand());

        verify(roleEvent).sendRoleCreatedEvent(any(RoleCreatedDomainEvent.class));
        verifyNoMoreInteractions(roleEvent);
    }

    @Test
    void handle_givenValidCommand_returnsVoidResponse() {
        when(pageRepository.findAllByIds(any())).thenReturn(RoleFixture.somePages());

        VoidResponse result = handler.handle(RoleFixture.aCreateRoleCommand());

        assertThat(result).isNotNull();
    }

    @Test
    void handle_givenEmptyPages_savesRoleWithEmptyPageList() {
        when(pageRepository.findAllByIds(any())).thenReturn(List.of());

        handler.handle(RoleFixture.aCreateRoleCommand());

        ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(roleCaptor.capture());
        assertThat(roleCaptor.getValue().getPages()).isEmpty();
    }

    @Test
    void getCommandType_returnsCreateRoleCommandClass() {
        assertThat(handler.getCommandType()).isEqualTo(CreateRoleCommand.class);
    }
}
