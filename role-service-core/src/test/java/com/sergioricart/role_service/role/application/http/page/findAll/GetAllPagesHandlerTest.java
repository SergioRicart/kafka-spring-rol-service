package com.sergioricart.role_service.role.application.http.page.findAll;

import com.sergioricart.role_service.fixtures.RoleFixture;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.port.PageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllPagesHandlerTest {

    @Mock
    private PageRepository pageRepository;

    @InjectMocks
    private GetAllPagesHandler handler;

    @Test
    void handle_givenExistingPages_returnsAllPages() {
        when(pageRepository.findAll()).thenReturn(RoleFixture.somePages());

        List<Page> result = handler.handle(new GetAllPagesQuery());

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(RoleFixture.somePages());
    }

    @Test
    void handle_givenNoPages_returnsEmptyList() {
        when(pageRepository.findAll()).thenReturn(List.of());

        List<Page> result = handler.handle(new GetAllPagesQuery());

        assertThat(result).isEmpty();
    }

    @Test
    void getCommandType_returnsGetAllPagesQueryClass() {
        assertThat(handler.getCommandType()).isEqualTo(GetAllPagesQuery.class);
    }
}
