package org.forumflow.backend.user.domain.entity;

import lombok.Getter;

@Getter
public enum TypeRole {
    USER(1),
    MODERATOR(2),
    ADMIN(3);

    private final int value;

    TypeRole(int value) {
        this.value = value;
    }
}
