package com.acai.just4fun;

import com.acai.just4fun.entity.Dish;
import com.acai.just4fun.stream.StreamDemo;
import com.acai.just4fun.stream.StreamPractice;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*List<Apple> appleList = Arrays.asList(
                new Apple(3, 3), new Apple(3, 5), new Apple(2, 6),
                new Apple(2,5)
        );

//        List<Apple> results = ListUtil.filter(appleList, (Apple apple) -> apple.getWeight() > 2 && apple.getColor() < 3);
//        List<Apple> results = ListUtil.filter(appleList, (Apple apple) -> apple.getWeight() > 100 && apple.getColor() < 3);
//        List<Integer> weights = ListUtil.map(appleList, Apple::getColor);
        appleList.sort(Comparator.comparing(Apple::getColor).reversed().thenComparing(Apple::getWeight));
        ListUtil.printList(appleList);*/

        List<Dish> menu = Arrays.asList(
                new Dish("vegetable", 20, false, Dish.Type.OTHER),
                new Dish("apple", 50, true, Dish.Type.OTHER),
                new Dish("beef", 200, false, Dish.Type.MEAT),
                new Dish("egg", 100, false, Dish.Type.MEAT),
                new Dish("oil", 500, true, Dish.Type.FISH)
        );
        /*
        StreamDemo.run(menu);*/

//        StreamDemo.testMap();

//        StreamDemo.testFlatMap();

//        StreamDemo.testFind(menu);

//        StreamDemo.testReduce();

        new StreamPractice().practice();
    }

    public void test() {
        int port = 8888;
        Runnable r = () -> System.out.println(port);
    }

}
