package com.blog.backend.config.pagination;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PaginationConfig {

    @Bean
    PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer() {
        return resolver -> {
            resolver.setMaxPageSize(50); // protect DB
            resolver.setFallbackPageable(PageRequest.of(0, 10));
            resolver.setOneIndexedParameters(false); // page starts from 0
        };
    }
}
