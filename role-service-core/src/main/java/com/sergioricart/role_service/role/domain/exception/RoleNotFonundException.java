package com.sergioricart.role_service.role.domain.exception;

public class RoleNotFonundException extends RuntimeException {
    public RoleNotFonundException(String message) {
        super(message);
    }

    public RoleNotFonundException(String message, String... parameter) {

        for(String param : parameter) {
            message = message.replaceAll("\\{" + param + "\\}", param);
        }
        throw new RoleNotFonundException(message);

    }
}
