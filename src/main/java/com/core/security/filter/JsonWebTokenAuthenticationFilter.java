package com.core.security.filter;

import com.core.core.constant.Constants;
import com.core.core.exception.error.ErrorCode;
import com.core.core.exception.type.CommonException;
import com.core.core.utility.HeaderUtil;
import com.core.core.utility.JsonWebTokenUtil;
import com.core.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import com.core.security.domain.type.ESecurityRole;
import com.core.security.info.CustomUserPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class JsonWebTokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticateJsonWebTokenUseCase authenticateJsonWebTokenUseCase;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        Claims claims = jsonWebTokenUtil.validateToken(token);

        UUID accountId = UUID.fromString(claims.get(Constants.USER_ID_CLAIM_NAME, String.class));
        ESecurityRole role = ESecurityRole.fromString(claims.get(Constants.USER_ROLE_CLAIM_NAME, String.class));

        CustomUserPrincipal principal = authenticateJsonWebTokenUseCase.execute(accountId);

        if (!role.equals(principal.getRole())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        // AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );

        // SecurityContext에 AuthenticationToken 저장
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        // 다음 필터로 전달
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        // 인증이 필요 없는 URL 목록에 포함되는지 확인
        return Constants.NO_NEED_AUTH_URLS.stream()
                .anyMatch(excludePattern -> requestURI.matches(excludePattern.replace("**", ".*")));
    }
}

