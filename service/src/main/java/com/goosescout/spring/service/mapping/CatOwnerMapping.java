package com.goosescout.spring.service.mapping;

import com.goosescout.spring.dao.entities.CatOwner;
import com.goosescout.spring.service.common.Role;
import com.goosescout.spring.service.dtos.CatOwnerDto;

import java.util.stream.Collectors;

public class CatOwnerMapping {
    public static CatOwnerDto asDto(CatOwner catOwner) {
        return new CatOwnerDto(
            catOwner.getId(),
            catOwner.getName(),
            catOwner.getUsername(),
            catOwner.getBirthDate(),
            catOwner.getRoles().stream().map(Role::fromId).collect(Collectors.toSet())
        );
    }
}
