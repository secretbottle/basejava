package ru.javawebinar;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStreames {
    public static void main(String[] args) {
        int[] numArr = new int[]{12, 130, 2, 2, 6, 9, 7, 11, 54, 33};

        System.out.println(minValue(numArr));

        System.out.println(
                oddOrEven(
                        IntStream.of(numArr).boxed().collect(Collectors.toCollection(ArrayList::new))
                ));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .filter(e -> 0 < e && e < 10)
                .distinct()
                .sorted()
                .reduce((acc, x) -> acc * 10 + x).orElse(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream().filter(x -> (sum % 2 == 0) == (x % 2 != 0)).collect(Collectors.toList());
    }

}
