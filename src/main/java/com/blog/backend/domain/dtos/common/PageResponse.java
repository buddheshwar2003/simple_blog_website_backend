package com.blog.backend.domain.dtos.common;

import org.springframework.data.domain.Page;
import java.util.List;

public record PageResponse<T>(
        List<T> postList,
        int page,
        int size,
        long totalItems,
        int totalPages,
        boolean last
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}

