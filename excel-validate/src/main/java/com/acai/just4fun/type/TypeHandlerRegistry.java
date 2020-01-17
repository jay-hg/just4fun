package com.acai.just4fun.type;

import org.apache.poi.ss.usermodel.CellType;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TypeHandlerRegistry {
    private final Map<CellType, TypeHandler<?>> cellTypeHandlerMap = new EnumMap<>(CellType.class);
    private final Map<Type, TypeHandler<?>> allTypeHandlersMap = new HashMap<>();

    public TypeHandlerRegistry() {
        cellTypeHandlerMap.put(CellType.NUMERIC, new DoubleTypeHandler());
        cellTypeHandlerMap.put(CellType.STRING, new StringTypeHandler());

        allTypeHandlersMap.put(String.class, new StringTypeHandler());
        allTypeHandlersMap.put(Double.class, new DoubleTypeHandler());
        allTypeHandlersMap.put(Integer.class, new IntegerTypeHandler());
    }

    public TypeHandler<?> getTypeHandler(CellType cellType) {
        return cellTypeHandlerMap.get(cellType);
    }

    public TypeHandler<?> getMappingTypeHandler(Type javaType) {
        return allTypeHandlersMap.get(javaType);
    }
}
