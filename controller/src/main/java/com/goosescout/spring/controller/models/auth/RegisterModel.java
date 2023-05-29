package com.goosescout.spring.controller.models.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record RegisterModel(
    @NotNull(message = "Name must not be null")
    String name,

    @NotNull(message = "Username must not be null")
    String username,

    @NotNull(message = "Password must not be null")
    String password,

    @NotNull(message = "Birth date must not be null")
    @PastOrPresent(message = "Birth date must be in the past or present")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate birthDate
) {
}
