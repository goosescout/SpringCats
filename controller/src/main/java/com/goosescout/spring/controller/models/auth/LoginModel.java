package com.goosescout.spring.controller.models.auth;

import jakarta.validation.constraints.NotNull;

public record LoginModel(
    @NotNull(message = "Username must not be null")
    String username,

    @NotNull(message = "Password must not be null")
    String password
) {
}
