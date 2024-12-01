package com.core.core.config;

import com.core.core.constant.Constants;
import com.core.core.interceptor.pre.HttpUserIDInterceptor;
import com.core.core.resolver.HttpUserIDArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMVCConfig implements WebMvcConfigurer {

    private final HttpUserIDInterceptor httpUserIDInterceptor;
    private final HttpUserIDArgumentResolver httpUserIDArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(httpUserIDArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(httpUserIDInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(Constants.NO_NEED_AUTH_URLS);
    }
}
