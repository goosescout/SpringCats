package com.goosescout.spring.service.common;

import lombok.Getter;

public enum Role {
    ADMIN(1),
    USER(2);

    @Getter
    private final int id;

    Role(int id) {
        this.id = id;
    }

    public static Role fromId(int id) {
        return switch (id) {
            case 1 -> Role.ADMIN;
            default -> Role.USER;
        };
    }
}
