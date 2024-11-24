package com.VEMS.vems.auth.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    HR_CREATE("hr:create"), HR_READ("hr:read"), HR_UPDATE("hr:update"), HR_DELETE("hr:delete"),
    MANAGER_CREATE("manager:create"), MANAGER_READ("manager:read"), MANAGER_UPDATE("manager:update"), MANAGER_DELETE("manager:delete"),
    SECURITY_CREATE("security:create"), SECURITY_READ("security:read"), SECURITY_UPDATE("security:update"), SECURITY_DELETE("security:delete"),
    OTHER_CREATE("other:create"), OTHER_READ("other:read"), OTHER_UPDATE("other:update"), OTHER_DELETE("other:delete");

    private final String permission;
}
