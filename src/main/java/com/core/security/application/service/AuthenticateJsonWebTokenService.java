package com.core.security.application.service;

import com.core.core.exception.error.ErrorCode;
import com.core.core.exception.type.CommonException;
import com.core.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import com.core.security.domain.mysql.User;
import com.core.security.domain.service.UserService;
import com.core.security.info.CustomUserPrincipal;
import com.core.security.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticateJsonWebTokenService implements AuthenticateJsonWebTokenUseCase {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public CustomUserPrincipal execute(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return userService.createCustomUserPrincipalByUser(user);
    }
}
