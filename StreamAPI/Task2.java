package StreamAPI;

import java.util.List;

public class Task2 {
    public static void main(String[] args) {
        List<String> words = List.of("java", "stream", "api", "lambda");

        List<String> list = words.stream()
                .filter(n -> n.length() > 3)
                .map(String::toUpperCase)
                .toList();

        list.forEach(System.out::println);
    }
}

/*
 *TODO:
 * Оставить строки длиной больше 3
 * Перевести их в верхний регистр
 * Собрать в список
 */