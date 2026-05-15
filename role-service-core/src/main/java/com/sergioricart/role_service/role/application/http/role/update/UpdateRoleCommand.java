package com.sergioricart.role_service.role.application.http.role.update;

import com.sergioricart.commons.application.Command;
import com.sergioricart.commons.application.VoidResponse;
import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleCommand implements Command<VoidResponse> {

    private String id;

    private String name;

    private String description;

    private List<String> pagesId;

}
