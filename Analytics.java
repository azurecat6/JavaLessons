package Shop;

import java.util.*;
import java.util.stream.Collectors;

public class Analytics {
    Map<String, List<Product>> groupByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));

    }
    Optional<Product> findMostExpensive(List<Product> products) {
        return products.stream()
                .max(Comparator.comparingDouble(Product::getPrice));
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
        List<Product> allProducts = new ArrayList<>(List.of(
                Product.builder().id(1).name("AirPods Pro").price(249.99).category("Electronics").build(),
                Product.builder().id(2).name("MacBook Air M2").price(1199.99).category("Electronics").build(),
                Product.builder().id(3).name("Samsung Galaxy S24").price(899.99).category("Electronics").build(),
                Product.builder().id(4).name("Sony WH-1000XM5").price(399.99).category("Electronics").build(),
                Product.builder().id(5).name("Dell XPS 13").price(1299.99).category("Electronics").build(),

                Product.builder().id(6).name("Кока-Кола 0.5л").price(1.49).category("Drinks").build(),
                Product.builder().id(7).name("Red Bull 0.25л").price(2.49).category("Drinks").build(),
                Product.builder().id(8).name("Чай Lipton зелёный").price(3.99).category("Drinks").build(),
                Product.builder().id(9).name("Кофе Lavazza Espresso").price(5.99).category("Drinks").build(),

                Product.builder().id(10).name("Яблоки Гренни Смит (1кг)").price(2.29).category("Food").build(),
                Product.builder().id(11).name("Бананы (1кг)").price(1.89).category("Food").build(),
                Product.builder().id(12).name("Куриная грудка (500г)").price(4.99).category("Food").build(),
                Product.builder().id(13).name("Молоко 3.2% (1л)").price(1.29).category("Food").build(),
                Product.builder().id(14).name("Хлеб белый нарезной").price(0.99).category("Food").build(),

                Product.builder().id(15).name("Adidas Ultraboost").price(179.99).category("Clothing").build(),
                Product.builder().id(16).name("Nike Air Force 1").price(119.99).category("Clothing").build(),
                Product.builder().id(17).name("Лонгслив Uniqlo").price(29.99).category("Clothing").build(),

                Product.builder().id(18).name("Книга 'Java. Полное руководство'").price(45.99).category("Books").build(),
                Product.builder().id(19).name("Книга 'Clean Code'").price(39.99).category("Books").build(),
                Product.builder().id(20).name("Книга 'Эффективный Java'").price(34.99).category("Books").build()
        ));


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


        Analytics analytics = new Analytics();

        Product mostExp = analytics.findMostExpensive(allProducts)
                .orElseThrow(() -> new IllegalArgumentException("Нет подходящего продукта"));
        System.out.println("Самый дорогой товар:" + mostExp.getName() +" "+ mostExp.getPrice() + "\n");

        System.out.println("=== ТОВАРЫ ПО КАТЕГОРИЯМ ===\n");
        analytics.groupByCategory(allProducts).forEach((category,products) -> {
            List<Product> productList = products.stream()
                    .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                    .toList();
            System.out.println(category.toUpperCase() + "(" + productList.size() + " товаров) :");
            for (Product p : productList) {
                System.out.printf("   %-35s %8.2f руб.%n", p.getName(), p.getPrice());
            }
                System.out.println();
    });

        List<String> tags = analytics.getAllTags(orders);
        System.out.println("\t" + "=== ДОСТУПНЫЕ ТЕГИ ===");
        System.out.println(tags + "\n");

        double sumRevenue = analytics.getTotalRevenue(orders);
        System.out.println(sumRevenue);

        System.out.println("=== СРЕДНЯЯ ЦЕНА ПО КАТЕГОРИЯМ ===");
        analytics.avgPriceByCategory(allProducts)
                .forEach((category, avgPrice) ->
                        System.out.printf("   %-12s : %.2f руб.%n", category, avgPrice)
                );

    }

}
