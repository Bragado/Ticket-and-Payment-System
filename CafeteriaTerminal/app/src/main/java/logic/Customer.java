package logic;

/**
 * Created by Miguel Lucas on 22/10/2018.
 */

public class Customer {
    private static int next_id = 1;
    private final int id;
    private String name;
    private String nif;
    private CreditCard creditCard;

    public Customer(String name, String nif) {
        id = next_id;
        next_id++;
        setName(name);
        setNif(nif);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
