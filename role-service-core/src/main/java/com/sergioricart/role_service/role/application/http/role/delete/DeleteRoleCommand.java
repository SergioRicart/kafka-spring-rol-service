package com.sergioricart.role_service.role.application.http.role.delete;

import com.sergioricart.commons.application.Command;
import com.sergioricart.commons.application.VoidResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteRoleCommand implements Command<VoidResponse> {

    private String id;

}
