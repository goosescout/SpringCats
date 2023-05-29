package com.goosescout.spring.controller.models.friendship;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record FriendshipModel(
    @NotNull(message = "Cat id must not be null")
    Long catId,
    @NotNull(message = "Friend id must not be null")
    Long friendId
) {
    @AssertTrue(message = "Cat id and friend id must not be equal")
    private boolean isFriendshipValid() {
        return !Objects.equals(catId, friendId);
    }
}
