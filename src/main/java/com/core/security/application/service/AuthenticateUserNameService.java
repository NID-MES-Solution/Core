package com.core.security.application.service;

import com.core.security.application.usecase.AuthenticateUserNameUseCase;
import com.core.security.domain.mysql.User;
import com.core.security.domain.service.UserService;
import com.core.security.domain.type.ESecurityProvider;
import com.core.security.repository.mysql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserNameService implements AuthenticateUserNameUseCase {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String serialId) throws UsernameNotFoundException {
        User user = userRepository.findBySerialIdAndProvider(serialId, ESecurityProvider.DEFAULT)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with serialId: " + serialId));

        return userService.createCustomUserPrincipalByUser(user);
    }
}
