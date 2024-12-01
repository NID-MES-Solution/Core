package com.core.security.application.usecase;

import com.core.core.annotation.bean.UseCase;
import com.core.security.application.dto.request.SignUpRequestDto;

@UseCase
public interface SignUpUseCase {
    void execute(SignUpRequestDto requestDto);
}
