package br.com.convoquei.backend._shared.helpers;

import jakarta.persistence.criteria.Join;
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

    public static <T> Specification<T> startsWithIgnoreCase(Join<?, ?> join, String field, String value) {
        return (root, query, cb) ->
                cb.like(cb.lower(join.get(field)), value.toLowerCase() + "%");
    }

    public static <T> Specification<T> equalEnum(String field, Enum<?> value) {
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    public static <T> Specification<T> equalEnum(Join<?, ?> join, String field, Enum<?> value) {
        return (root, query, cb) -> cb.equal(join.get(field), value);
    }
}