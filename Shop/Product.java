package Shop;

public class Product {
    private int id;
    private String name;
    private double price;
    private String category;

    @Override
    public String toString() {
        return "Product: " +
                "name ='" + name + '\'' +
                ", price= " + price + '\'';
    }

    public Product(ProductBuilder builder) {
        id = builder.id;
        name = builder.name;
        price = builder.price;
        category = builder.category;
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

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

   public static class  ProductBuilder {
        private int id;
        private String name;
        private double price;
        private String category;

        public ProductBuilder id(int id) {
            this.id = id;
            return this;
        }
        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }
        public ProductBuilder price(double price) {
            this.price = price;
            return this;
        }
        public ProductBuilder category(String category) {
            this.category = category;
            return this;
        }
        public Product build() {
            return new Product(this);
        }
    }
}
