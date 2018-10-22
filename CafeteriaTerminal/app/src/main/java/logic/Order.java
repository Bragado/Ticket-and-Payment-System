package logic;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Miguel Lucas on 21/10/2018.
 */

public class Order implements Serializable{
    //product - number of items ordered
    private HashMap<Product, Integer> products;
    private float price;
    private int client_id;
    private boolean orderServed;

    public Order(int client_id){
        this.client_id = client_id;
        products = new HashMap<>();
        setOrderServed(false);
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public boolean getOrderServed() {
        return orderServed;
    }

    public void setOrderServed(boolean orderServed) {
        this.orderServed = orderServed;
    }

    public void addProduct(Product product) {
        product.setSales(product.getSales() + 1);
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }

        price += product.getPrice();
    }

    public OrderType getType(){
        String productName = "";
        for (HashMap.Entry<Product, Integer> pair : products.entrySet()) {
            if (productName.equals("")) {
                productName = pair.getKey().getName();
                continue;
            }

            if (!productName.equals(pair.getKey().getName())){
                return OrderType.MIXED;
            }
        }

        switch (productName){
            case "Coffee":
                return OrderType.COFFEE;
            case "Soda":
                return OrderType.SODA;
            case "Sandwich":
                return OrderType.SANDWICH;
            case "Popcorn":
                return OrderType.POPCORN;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        String res = "";

        for (HashMap.Entry<Product, Integer> pair : products.entrySet()) {
            if (!res.equals(""))
                res += ", ";

            res += pair.getValue() + " " + pair.getKey().getName();
        }

        return res;
    }


}
