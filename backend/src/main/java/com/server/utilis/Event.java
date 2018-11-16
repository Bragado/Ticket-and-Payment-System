package com.server.utilis;

public class Event {
	public int ID;
	public String photoName;
	public String Title;
	public String Description;
	public String date;
	public int Capacity;
	public int ticketPrice;

	public Event(int ID, String PhotoName, String Title, String Description, String Date, int Capacity, int ticketPrice) {
		this.ID = ID;
		this.photoName = PhotoName;
		this.Title = Title;
		this.Description = Description;
		this.date = Date;
		this.Capacity = Capacity;
		this.ticketPrice = ticketPrice;
	}

	public Event(String title, String date, int Capacity, int ticketPrice) {
		this.Title =title;
		this.date = date;
		this.Capacity = Capacity;
		this.ticketPrice = ticketPrice;
	}
	
}
