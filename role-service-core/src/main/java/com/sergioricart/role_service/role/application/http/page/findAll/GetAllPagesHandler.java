package com.sergioricart.role_service.role.application.http.page.findAll;

import com.sergioricart.commons.application.CommandHandler;
import com.sergioricart.role_service.role.domain.entity.Page;
import com.sergioricart.role_service.role.domain.port.PageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetAllPagesHandler implements CommandHandler<GetAllPagesQuery, List<Page>> {

    private final PageRepository pageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Page> handle(GetAllPagesQuery query) {
        log.info("Getting all pages");
        return pageRepository.findAll();
    }

    @Override
    public Class<GetAllPagesQuery> getCommandType() {
        return GetAllPagesQuery.class;
    }
}
