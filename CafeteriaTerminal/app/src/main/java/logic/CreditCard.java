package logic;

import java.util.Date;

/**
 * Created by Miguel Lucas on 22/10/2018.
 */

public class CreditCard {
    private String type;
    private int number;
    private Date validity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }
}
