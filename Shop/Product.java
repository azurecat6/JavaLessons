package Shop;

public class Product {
    private int id;
    private String name;
    private double price;
    private String category;

    @Override
    public String toString() {
        return "Product: " +
                "id = " + id +
                ", name ='" + name + '\'' +
                ", price= " + price +
                ", category ='" + category + '\'';
    }

    public Product(int id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    static class  ProductBuilder {
        private int id;
        private String name;
        private double price;
        private String category;


    }
}
