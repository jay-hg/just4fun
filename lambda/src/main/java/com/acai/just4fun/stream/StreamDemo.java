package com.acai.just4fun.stream;

import com.acai.just4fun.entity.Dish;
import com.acai.just4fun.functional.ListUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamDemo {
    public static void run(List<Dish> menu) {
        List<String> list = menu.parallelStream()
                .filter(d -> d.getCalories() > 100)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(Collectors.toList());
        ListUtil.printList(list);
    }

    public static void testMap() {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> resultList = numList.parallelStream()
                .map(i -> i*i)
                .collect(Collectors.toList());
        ListUtil.printList(resultList);
    }

    public static void testFlatMap() {
        List<String> arrayOfWords = Arrays.asList("hello","world");
        arrayOfWords.parallelStream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public static void testFind(List<Dish> menu) {
            menu.stream().filter(Dish::isVegetarian)
                .findAny().ifPresent(dish -> System.out.println(dish.getName()));
    }

    public static void testReduce() {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numList.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);
    }
}
