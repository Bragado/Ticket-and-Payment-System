package cmov.miguellucas.com.cafeteriaterminal;

/**
 * Created by Miguel Lucas on 21/10/2018.
 */

public class Product {
    private String name;
    private double price;
    private int sales;

    public Product(String name, double price){
        this.setName(name);
        this.setPrice(price);
        this.setSales(0);
    }

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
}
