package com.goosescout.spring.service.dtos;

import com.goosescout.spring.service.common.CatColor;

import java.time.LocalDate;
import java.util.List;

public record CatDto(Long id, String name, LocalDate birthDate, String breed, CatColor color, Long catOwnerId, List<Long> friendIds) {
}
