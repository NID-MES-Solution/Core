package com.core.security.application.service;

import com.core.security.application.usecase.LogoutUseCase;
import com.core.security.info.CustomUserPrincipal;
import com.core.security.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutUseCase {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal) {
        UUID userId = principal.getId();

        refreshTokenRepository.deleteById(userId);
    }
}
