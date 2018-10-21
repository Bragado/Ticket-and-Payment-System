package cmov.miguellucas.com.cafeteriaterminal;

import java.util.ArrayList;

/**
 * Created by Miguel Lucas on 21/10/2018.
 */

public class Order {
    private ArrayList<Product> products;
    private double price;
    private int client_id;

    public Order(int client_id){
        this.client_id = client_id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public void addProduct(Product product) {
        products.add(product);
        price += product.getPrice();
    }
}
