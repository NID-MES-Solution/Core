package com.core.security.application.dto.request;

import com.core.security.domain.type.ESecurityRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpRequestDto(

        @NotNull
        @Size(min = 1, max = 20)
        @JsonProperty("serial_id")
        String serialId,

        @NotNull
        @Size(min = 6, max = 320)
        @JsonProperty("password")
        String password,

        @NotNull
        @JsonProperty("name")
        String name,

        @NotNull
        @JsonProperty("role")
        ESecurityRole role
){
}
