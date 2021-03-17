package com.acai.just4fun.functional;

@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}
