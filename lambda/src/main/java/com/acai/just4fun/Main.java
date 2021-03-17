package com.acai.just4fun;

import com.acai.just4fun.entity.Apple;
import com.acai.just4fun.functional.ListUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Apple> appleList = Arrays.asList(
                new Apple(3, 3), new Apple(3, 5), new Apple(2, 6),
                new Apple(2,5)
        );

//        List<Apple> results = ListUtil.filter(appleList, (Apple apple) -> apple.getWeight() > 2 && apple.getColor() < 3);
//        List<Apple> results = ListUtil.filter(appleList, (Apple apple) -> apple.getWeight() > 100 && apple.getColor() < 3);
//        List<Integer> weights = ListUtil.map(appleList, Apple::getColor);
        appleList.sort(Comparator.comparing(Apple::getColor).reversed().thenComparing(Apple::getWeight));
        ListUtil.printList(appleList);
    }

    public void test() {
        int port = 8888;
        Runnable r = () -> System.out.println(port);
    }

}
