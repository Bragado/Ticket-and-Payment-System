package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Cafeteria implements Serializable {
    //Singleton instance
    private static final Cafeteria instance = new Cafeteria();
    public static Cafeteria getInstance() {
        return instance;
    }

    private ArrayList<Product> products;
    private ArrayList<Order> orders;


    private Cafeteria() {
        products = new ArrayList<>();
        orders = new ArrayList<>();
        startCafeteria();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void startCafeteria() {
        Product coffee = new Product(Product.COFFEE);
        Product soda = new Product(Product.SODA);
        Product popcorn = new Product(Product.POPCORN);
        Product sandwich = new Product(Product.SANDWICH);
        coffee.setPrice(0.6);
        soda.setPrice(1.2);
        popcorn.setPrice(2.5);
        sandwich.setPrice(3);

        products.add(coffee);
        products.add(soda);
        products.add(popcorn);
        products.add(sandwich);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
