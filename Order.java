package Shop;

import java.util.List;

public class Order {
    private int orderId;
    private String customerName;
    private List<Product> products;
    private List<String> tags;

    public Order(int orderId, String customerName, List<Product> products, List<String> tags) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.products = products;
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getOrderId() {
        return orderId;
    }
}
