package cmov.miguellucas.com.customerapp.Models;

import java.io.Serializable;

public class Event implements Serializable {

    public String photoName;
    public String title;
    public String Description;
    public String date;
    public int capacity, ticketPrice, ID;


    public Event(int ID, int capacity, int ticketPrice, String photoName, String title, String description, String date) {
        this.photoName = photoName;
        this.title = title;
        this.Description = description;
        this.date = date;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
        this.ID = ID;
    }



}
