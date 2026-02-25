package br.com.convoquei.backend._shared.helpers;

import org.springframework.data.jpa.domain.Specification;

public final class SpecHelper {
    private SpecHelper() {}

    public static <T> Specification<T> alwaysTrue() {
        return (root, query, cb) -> cb.conjunction();
    }

    public static boolean hasText(String s) {
        return s != null && !s.isBlank();
    }

    public static <T> Specification<T> containsIgnoreCase(String field, String value) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    public static <T> Specification<T> startsWithIgnoreCase(String field, String value) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(field)), value.toLowerCase() + "%");
    }
}