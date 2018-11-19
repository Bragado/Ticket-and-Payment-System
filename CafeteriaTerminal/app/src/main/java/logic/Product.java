package logic;

import java.io.Serializable;

public class Product implements Serializable {

    public static final int COFFEE = 1;
    public static final int SODA = 2;
    public static final int POPCORN = 3;
    public static final int SANDWICH = 4;
    private int id;
    private String name;
    private double price;
    private int sales;

    public Product(int id, double price){
        this.id = id;
        this.price = price;
        this.sales = 0;

        switch (id){
            case COFFEE:
                name = "Coffee";
                break;
            case SODA:
                name = "Soda";
                break;
            case POPCORN:
                name = "Popcorn";
                break;
            case SANDWICH:
                name = "Sandwich";
                break;
            default:
                name = "Unknown";
                break;
        }
    }

    public int getId(){ return id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Product))
            return false;

        Product otherProduct = (Product) other;

        return otherProduct.getName().equals(this.getName());
    }
}
