package com.goosescout.spring.controller.models.cat;

import com.goosescout.spring.service.common.CatColor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AddCatModel(
    @NotNull(message = "Name must not be null")
    String name,
    @NotNull(message = "Birth date must not be null")
    @PastOrPresent(message = "Birth date must be in the past or present")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate birthDate,
    String breed,
    @NotNull(message = "Color must not be null")
    CatColor color
) {
}
