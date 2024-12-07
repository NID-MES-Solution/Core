package com.core.security.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ESecurityRole {

    STUDENT("학생", "STUDENT", "ROLE_STUDENT"),
    ADMIN("관리자", "ADMIN", "ROLE_ADMIN")
    ;

    private final String koName;
    private final String enName;
    private final String securityName;

    public static ESecurityRole fromString(String value) {
        return switch (value.toUpperCase()) {
            case "STUDENT" -> STUDENT;
            case "ADMIN" -> ADMIN;
            default -> throw new IllegalArgumentException("Security Role이 잘못되었습니다.");
        };
    }
}
