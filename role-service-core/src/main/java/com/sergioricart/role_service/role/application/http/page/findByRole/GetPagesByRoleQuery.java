package com.sergioricart.role_service.role.application.http.page.findByRole;

import com.sergioricart.commons.application.Command;
import com.sergioricart.role_service.role.domain.entity.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetPagesByRoleQuery implements Command<List<Page>> {

    private String roleId;

}