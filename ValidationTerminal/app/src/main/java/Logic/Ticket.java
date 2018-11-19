package Logic;

import java.io.Serializable;

public class Ticket implements Serializable {
    public final static int NOT_VERIFIED = 0;
    public final static int VALID = 1;
    public final static int REJECTED = 2;

    private String id;
    private String userId;
    private String eventId;
    private int status;

    public Ticket(String id, String userId, String eventId){
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        status = NOT_VERIFIED;
    }

    public Ticket(String id, String userId){
        this.id = id;
        this.userId = userId;
        this.eventId = "22";
        status = NOT_VERIFIED;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
