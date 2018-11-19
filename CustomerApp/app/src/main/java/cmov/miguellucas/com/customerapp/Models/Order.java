package cmov.miguellucas.com.customerapp.Models;

import java.util.ArrayList;

public class Order {

    public ArrayList<Product> products;
    public int ID;
    public double price;
    public String date;

    public Order(int ID, double price, String date) {
        this.price = price;
        this.ID = ID;
        this.date = date;
    }

}
