package com.core.security.application.service;

import com.core.security.application.usecase.DeleteUserUseCase;
import com.core.security.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final UserRepository userRepository;

    @Override
    public void execute(UUID userId) {
        userRepository.deleteById(userId);
    }
}
