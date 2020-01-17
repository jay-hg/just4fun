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
        TypeHandler<?> typeHandler = allTypeHandlersMap.get(javaType);
        if (typeHandler == null && javaType instanceof Class) {

            Class<?> clazz = (Class<?>) javaType;
            if (Enum.class.isAssignableFrom(clazz)) {
                Class<?> enumClass = clazz.isAnonymousClass() ? clazz.getSuperclass() : clazz;
                allTypeHandlersMap.put(enumClass, new EnumTypeHandler(enumClass));
                return allTypeHandlersMap.get(enumClass);
            }
        }
        return typeHandler;
    }
}
