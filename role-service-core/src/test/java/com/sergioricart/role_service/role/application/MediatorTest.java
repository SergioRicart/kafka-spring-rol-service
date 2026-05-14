package com.sergioricart.role_service.role.application;

import com.sergioricart.commons.application.CommandHandler;
import com.sergioricart.commons.application.Mediator;
import com.sergioricart.commons.application.VoidResponse;
import com.sergioricart.role_service.fixtures.RoleFixture;
import com.sergioricart.role_service.role.application.http.role.created.CreateRoleCommand;
import com.sergioricart.role_service.role.application.http.role.delete.DeleteRoleCommand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class MediatorTest {

    @Test
    @SuppressWarnings("unchecked")
    void dispatch_givenRegisteredCommand_invokesCorrectHandler() {
        CommandHandler<CreateRoleCommand, VoidResponse> handler = mock(CommandHandler.class);
        when(handler.getCommandType()).thenReturn(CreateRoleCommand.class);

        CreateRoleCommand command = new CreateRoleCommand();
        VoidResponse expected = new VoidResponse();
        when(handler.handle(command)).thenReturn(expected);

        Mediator mediator = new Mediator(List.of(handler));

        VoidResponse result = mediator.dispatch(command);

        assertThat(result).isSameAs(expected);
        verify(handler).handle(command);
    }

    @Test
    @SuppressWarnings("unchecked")
    void dispatch_givenMultipleHandlers_routesToTheCorrectOne() {
        CommandHandler<CreateRoleCommand, VoidResponse> createHandler = mock(CommandHandler.class);
        when(createHandler.getCommandType()).thenReturn(CreateRoleCommand.class);

        CommandHandler<DeleteRoleCommand, VoidResponse> deleteHandler = mock(CommandHandler.class);
        when(deleteHandler.getCommandType()).thenReturn(DeleteRoleCommand.class);

        Mediator mediator = new Mediator(List.of(createHandler, deleteHandler));

        DeleteRoleCommand deleteCommand = new DeleteRoleCommand(RoleFixture.ROLE_ID);
        mediator.dispatch(deleteCommand);

        verify(deleteHandler).handle(deleteCommand);
        verify(createHandler, never()).handle(any());
    }

    @Test
    void dispatch_givenUnregisteredCommand_throwsRuntimeException() {
        Mediator mediator = new Mediator(List.of());

        assertThatThrownBy(() -> mediator.dispatch(new CreateRoleCommand()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No handler found");
    }
}
