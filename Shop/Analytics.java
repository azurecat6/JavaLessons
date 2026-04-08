package Shop;

import java.util.*;
import java.util.stream.Collectors;

public class Analytics {
    Map<String, List<Product>> groupByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
        //Тут немного не поняла,
        // второй параметр (Collector) — это что делать со значениями каждой группы.
        // Без него используется toList() по умолчанию.
        //То есть нужно явно привести к мапе?
    }
    Optional<Product> findMostExpensive(List<Product> products) {
        Optional<Product> mostExpensive = products.stream()
                .max(Comparator.comparingDouble(Product::getPrice));
        //Пришлось прибегнуть к помощи Ии, тк я не смогла понять как работает max и ifPresent(я не знала что он не возвращает значение)
        mostExpensive.ifPresent(p ->
                System.out.println("Most expensive product is : " + p)
        );
        return mostExpensive;
    }

    List<String> getAllTags(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getTags().stream())
                .distinct()
                .sorted()
                .toList();
    }

    double getTotalRevenue(List<Order> orders) {
        return orders.parallelStream()
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();
    }

    Map<String, Double> avgPriceByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.averagingDouble(Product::getPrice)));
    }

    public static void main(String[] args) {
        List<Product> allProducts = new ArrayList<>();

        allProducts.add(new Product(1, "Airpods Pro", 249.99, "Electronics"));
        allProducts.add(new Product(2, "MacBook Air M2", 1199.99, "Electronics"));
        allProducts.add(new Product(3, "Samsung Galaxy S24", 899.99, "Electronics"));
        allProducts.add(new Product(4, "Sony WH-1000XM5", 399.99, "Electronics"));
        allProducts.add(new Product(5, "Dell XPS 13", 1299.99, "Electronics"));

        allProducts.add(new Product(6, "Кока-Кола 0.5л", 1.49, "Drinks"));
        allProducts.add(new Product(7, "Red Bull 0.25л", 2.49, "Drinks"));
        allProducts.add(new Product(8, "Чай Lipton зелёный", 3.99, "Drinks"));
        allProducts.add(new Product(9, "Кофе Lavazza Espresso", 5.99, "Drinks"));

        allProducts.add(new Product(10, "Яблоки Гренни Смит (1кг)", 2.29, "Food"));
        allProducts.add(new Product(11, "Бананы (1кг)", 1.89, "Food"));
        allProducts.add(new Product(12, "Куриная грудка (500г)", 4.99, "Food"));
        allProducts.add(new Product(13, "Молоко 3.2% (1л)", 1.29, "Food"));
        allProducts.add(new Product(14, "Хлеб белый нарезной", 0.99, "Food"));

        allProducts.add(new Product(15, "Adidas Ultraboost", 179.99, "Clothing"));
        allProducts.add(new Product(16, "Nike Air Force 1", 119.99, "Clothing"));
        allProducts.add(new Product(17, "Лонгслив Uniqlo", 29.99, "Clothing"));

        allProducts.add(new Product(18, "Книга 'Java. Полное руководство'", 45.99, "Books"));
        allProducts.add(new Product(19, "Книга 'Clean Code'", 39.99, "Books"));
        allProducts.add(new Product(20, "Книга 'Эффективный Java'", 34.99, "Books"));//че-то слииишком нагружено, я не знаю как сделать это более компактно

        List<Order> orders = new ArrayList<>();

        orders.add(new Order(120, "Charlie",
                List.of(allProducts.get(1), allProducts.get(2), allProducts.get(3), allProducts.get(4)),
                List.of("срочно")));

        orders.add(new Order(121, "Anna",
                List.of(allProducts.get(5), allProducts.get(6), allProducts.get(13), allProducts.get(18)),
                List.of("доставка вечером")));

        orders.add(new Order(122, "Michael",
                List.of(allProducts.get(2), allProducts.get(4), allProducts.get(15), allProducts.get(16)),
                List.of("подарок", "упаковать красиво")));

        orders.add(new Order(123, "Sophia",
                List.of(allProducts.get(10), allProducts.get(11), allProducts.get(12), allProducts.get(13), allProducts.get(14)),
                List.of("срочно", "без звонка")));

        orders.add(new Order(124, "David",
                List.of(allProducts.get(0), allProducts.get(3), allProducts.get(7), allProducts.get(19)),
                List.of()));
        // тут тоже слишком нагружено, не знаю как создавать лаконично заказы

        Analytics analytics = new Analytics();
        Product mostExp = analytics.findMostExpensive(allProducts)
                .orElseThrow(() -> new IllegalArgumentException("Нет подходящего продукта"));
        //не поняла как именно использовать orElseThrow,
        // как я поняла, если не будет значения выкинется NoSuchElementException, а если будет то оно выведется
        System.out.println(mostExp);

        Map<String, List<Product>> sortedProducts = analytics.groupByCategory(allProducts);
        System.out.println(sortedProducts);

        List<String> tags = analytics.getAllTags(orders);
        System.out.println(tags);

        double sumRevenue = analytics.getTotalRevenue(orders);
        System.out.println(sumRevenue);

        Map<String, Double> avgPrice = analytics.avgPriceByCategory(allProducts);
        System.out.println(avgPrice);


    }

}
