package com.core.security.application.service;

import com.core.security.application.dto.response.DefaultJsonWebTokenDto;
import com.core.security.application.usecase.LoginUseCase;
import com.core.security.domain.service.RefreshTokenService;
import com.core.security.info.CustomUserPrincipal;
import com.core.security.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal, DefaultJsonWebTokenDto jsonWebTokenDto) {

        UUID userId = principal.getId();
        String refreshToken = jsonWebTokenDto.getRefreshToken();

        // Refresh Token 저장
        if (refreshToken != null) {
            refreshTokenRepository.save(refreshTokenService.createRefreshToken(userId, refreshToken));
        }
    }
}
