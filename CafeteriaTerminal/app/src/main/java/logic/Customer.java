package logic;

import java.io.Serializable;

public class Customer implements Serializable {
    private int id;
    private String name;
    private String nif;

    public Customer(String name, String nif) {
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

}
