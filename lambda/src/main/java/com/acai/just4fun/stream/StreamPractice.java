package com.acai.just4fun.stream;

import com.acai.just4fun.entity.Trader;
import com.acai.just4fun.entity.Transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StreamPractice {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("mario ", "Milan");
    Trader alan = new Trader("alan ", "Cambridge");
    Trader brian = new Trader("brian ", "Cambridge");
    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    public void practice() {
        /*transactions.stream().filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .forEach(System.out::println);
        */

       /* transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct().forEach(System.out::println);
        */

       /*transactions.stream().filter(transaction -> transaction.getTrader().getCity() == "Cambridge")
               .map(transaction -> transaction.getTrader())
               .distinct()
               .sorted(Comparator.comparing(Trader::getName)).forEach(System.out::println);
       */

      /* transactions.stream().map(transaction -> transaction.getTrader().getName())
               .distinct()
               .sorted().forEach(System.out::println);
       */

     /* transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Milan"))
              .findAny().ifPresent(System.out::println);
      */

       /* int sum = transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getValue())
                .reduce(0, Integer::sum);
        System.out.println(sum);
        */

       /* transactions.stream().map(transaction -> transaction.getValue())
                .reduce(Integer::max).ifPresent(max -> System.out.println(max));
        */

        transactions.stream()
                .min(Comparator.comparing(Transaction::getValue)).ifPresent(transaction -> System.out.println(transaction.getValue()));
    }
}
