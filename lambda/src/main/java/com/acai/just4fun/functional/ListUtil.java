package com.acai.just4fun.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ListUtil {
    public static <T> List<T> filter(List<T> list, java.util.function.Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if (predicate.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    public static <T> void printList(List<T> list) {
        list.forEach((T e) -> System.out.println(e));
//        forEach(list,(T e) -> System.out.println(e));
    }

    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for (T i : list) {
            consumer.accept(i);
        }
    }

    public static <T, R> List<R> map(List<T> list, Function<T,R> function) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(function.apply(t));
        }
        return result;
    }
}
