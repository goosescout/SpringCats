package com.goosescout.spring.controller.models.owner;

import com.goosescout.spring.service.common.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;

public record UpdateCatOwnerModel(
    @NotNull(message = "Id must not be null")
    Long id,
    @NotNull(message = "Name must not be null")
    String name,

    @NotNull(message = "Username must not be null")
    String username,

    @NotNull(message = "Password must not be null")
    String password,

    @NotNull(message = "Birth date must not be null")
    @PastOrPresent(message = "Birth date must be in the past or present")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate birthDate,

    @NotNull(message = "Roles must not be null")
    ArrayList<Role> roles
) {
}
