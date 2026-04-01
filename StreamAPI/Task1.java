package StreamAPI;
import java.util.List;


public class Task1 {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7);

        List<Integer> stream =  numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n*10)
                .toList();

        for(int n : stream){
            System.out.println(n);
        }
    }
}

/*
 *TODO:Задача 1
 * Дан список чисел.
 * List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7);
 * С помощью Stream API:
 * 1. Оставить только чётные числа
 * 2. Умножить их на 10
 * 3. Собрать в новый список
 * Ожидаемый результат:
 * [20, 40, 60]
 */
