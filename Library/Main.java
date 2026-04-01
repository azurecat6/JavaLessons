package Library;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        library.addItem(new Book(1, "Java для начинающих", 2022, "Сергей Иванов", 450));
        library.addItem(new Book(2, "Чистый код", 2008, "Роберт Мартин", 464));
        library.addItem(new Book(3, "Java. Полное руководство", 2023, "Герберт Шилдт", 1488));
        library.addItem(new Book(4, "Эффективная Java", 2018, "Джошуа Блох", 392));

        library.addItem(new Magazine(5, "National Geographic", 2024, 3, true));
        library.addItem(new Magazine(6, "Forbes", 2025, 1, false));
        library.addItem(new Magazine(7, "Java Magazine", 2023, 12, true));

        System.out.println("Отсортированный список:");
        library.getAllSorted().forEach(item ->
                System.out.println(item.getYear() + " - " + item.getTitle())
        );

        System.out.println("\nПоиск по 'java':");
        library.search("java").forEach(item ->
                System.out.println(item.getTitle())
        );

        System.out.println("\nПоиск по 'код':");
        library.search("код").forEach(item ->
                System.out.println(item.getTitle())
        );
    }
}
