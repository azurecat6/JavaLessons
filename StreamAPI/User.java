package StreamAPI;

import java.util.List;
import java.util.stream.Collectors;

public class User {
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }

    public static void main(String[] args) {
        List<User> users = List.of(
                new User("Alice", 20),
                new User("Bob", 17),
                new User("Charlie", 25),
                new User("David", 16)
        );
        List<String> names = users.stream()
                .filter(p -> p.getAge() > 18)
                .map(User::getName)
                .toList();

        names.forEach(System.out::println);
    }
}
