package com.goosescout.spring.service.dtos;

import com.goosescout.spring.service.common.Role;

import java.time.LocalDate;
import java.util.Set;

public record CatOwnerDto(Long id, String name, String username, LocalDate birthDate, Set<Role> roles) {
}
