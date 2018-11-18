package cmov.miguellucas.com.customerapp.Models;

public class Ticket {
    public String ID;
    public int Place;
    public String eventTitle;
    public String date;

    public Ticket(String ID, int place, String eventTitle, String date) {
        this.ID = ID;
        this.Place = place;
        this.eventTitle = eventTitle;
        this.date = date;
    }

}
