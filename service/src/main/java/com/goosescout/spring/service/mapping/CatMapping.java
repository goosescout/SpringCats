package com.goosescout.spring.service.mapping;

import com.goosescout.spring.dao.entities.Cat;
import com.goosescout.spring.service.common.CatColor;
import com.goosescout.spring.service.dtos.CatDto;

public class CatMapping {
    public static CatDto asDto(Cat cat) {
        return new CatDto(
            cat.getId(),
            cat.getName(),
            cat.getBirthDate(),
            cat.getBreed(),
            CatColor.fromColor(cat.getColor()),
            cat.getOwner().getId(),
            cat.getFriends().stream().map(Cat::getId).toList()
        );
    }
}
