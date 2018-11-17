package com.server.utilis;

public class Ticket {
	public String ticketID;
	public int eventID;
	public String ownerID;
	public int used;
	public int place;
	
	public Ticket(String ticketID, String ownerID, int eventID, int used) {
		this.ticketID = ticketID;
		this.ownerID = ownerID;
		this.used = used;
		this.eventID = eventID;
	}
	

}
