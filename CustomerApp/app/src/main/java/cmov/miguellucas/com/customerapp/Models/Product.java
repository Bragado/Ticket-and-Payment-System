package cmov.miguellucas.com.customerapp.Models;

public class Product {
   public int quantity;
    public int type;

    public Product(int type, int quantity) {
        this.type = type; this.quantity = quantity;
    }

    public String getName() {
        switch(type) {
            case 1:
                return "Coffe";
            case 2:
                return "Soda";
            case 3:
                return "PopCorn";
            case 4:
                return "Sandwich";
        }
        return "";
    }


}
