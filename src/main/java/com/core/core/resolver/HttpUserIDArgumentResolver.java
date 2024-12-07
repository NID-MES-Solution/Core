package com.core.core.resolver;

import com.core.core.annotation.security.UserID;
import com.core.core.constant.Constants;
import com.core.core.exception.error.ErrorCode;
import com.core.core.exception.type.CommonException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@Component
public class HttpUserIDArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UUID.class)
                && parameter.hasParameterAnnotation(UserID.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        String userAccountId = (String) webRequest.getAttribute(
                Constants.USER_ID_ATTRIBUTE_NAME,
                WebRequest.SCOPE_REQUEST
        );

        if (userAccountId == null) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        return UUID.fromString(userAccountId);
    }
}
