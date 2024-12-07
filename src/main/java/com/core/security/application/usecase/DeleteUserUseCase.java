package com.core.security.application.usecase;

import com.core.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteUserUseCase {

    /**
     * 계정 탈퇴
     * @param userId 유저 ID
     */
    void execute(UUID userId);
}
