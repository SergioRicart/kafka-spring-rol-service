package com.sergioricart.role_service.role.domain.port;

import com.sergioricart.role_service.role.domain.entity.Page;

import java.util.List;

public interface PageRepository {

    List<Page> findAllByIds(List<String> ids);

}
