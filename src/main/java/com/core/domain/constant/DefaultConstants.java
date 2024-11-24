package com.core.domain.constant;

import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

public class DefaultConstants {

    // JWT
    public static String USER_ID_ATTRIBUTE = "USER_ID";
    public static String USER_ID_CLAIM_NAME = "uid";
    public static String USER_ROLE_CLAIM_NAME = "role";

    // HEADER
    public static String BEARER_TOKEN_PREFIX = "Bearer";
    public static String AUTHORIZATION_HEADER = "Authorization";

    // COOKIE
    public static String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static String REFRESH_TOKEN = "REFRESH_TOKEN";

    // 인증 필요 없는 URL
    public static List<String> NO_REQUIREMENT_AUTH_URLS = List.of(
            // Authentication
            "/api/v1/auth/reissue/token",
            "/api/v1/auth/sign-up",
            "/api/v1/auth/login",
            "/api/v1/auth/users",

            // Swagger
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v3/**"
    );

    // Swagger url
    public static List<String> SWAGGER_URLS = List.of(
            "/api-docs.html",
            "/api-docs",
            "/swagger-ui",
            "/v3"
    );

    // 사용자 url
    public static List<String> USER_URLS = List.of(
            "/api/v1/**"
    );
}
