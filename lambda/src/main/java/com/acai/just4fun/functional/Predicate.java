package com.acai.just4fun.functional;

/**
 * 模仿java.util.function.Predicate
 * @param <T>
 */
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
