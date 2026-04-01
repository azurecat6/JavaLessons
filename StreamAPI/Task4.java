package StreamAPI;

import java.util.List;

public class Task4 {
    public static void main(String[] args) {
        List<List<Integer>> numbers = List.of(
                List.of(4, 7, 2, 9, 1),
                List.of(12, 3, 8, 5, 11),
                List.of(6, 13, 10, 15, 14),
                List.of(21, 18, 17, 19, 16)
        );
        List<Integer> result = numbers.stream()
                .flatMap(List::stream)
                .filter(e -> e % 2 != 0)
                .sorted()
                .toList();
        System.out.println(result);
    }
}

/*
 *TODO:
 * Дан список списков чисел:
   List<List<Integer>> numbers = List.of(
    List.of(4, 7, 2, 9, 1),
    List.of(12, 3, 8, 5, 11),
    List.of(6, 13, 10, 15, 14),
    List.of(21, 18, 17, 19, 16)
    * );
    Нужно:
    * 1. Развернуть все списки в один
    * 2. Оставить только нечётные числа
    * 3. Отсортировать
    * 4. Собрать в список
 */
