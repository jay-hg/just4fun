package com.acai.just4fun.type;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class TypeHandlerRegistry {
    private final Map<Type, TypeHandler<?>> allTypeHandlersMap = new HashMap<>();

    public TypeHandlerRegistry() {
        allTypeHandlersMap.put(String.class, new StringTypeHandler());
        allTypeHandlersMap.put(Double.class, new DoubleTypeHandler());
        allTypeHandlersMap.put(Integer.class, new IntegerTypeHandler());
    }

    public boolean hasTypeHandler(Type javaType) {
        return javaType != null && getTypeHandler(javaType) != null;
    }

    public TypeHandler<?> getTypeHandler(Type javaType) {
        return allTypeHandlersMap.get(javaType);
    }
}
