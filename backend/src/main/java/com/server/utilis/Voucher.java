package com.server.utilis;
 
public class Voucher {
	
	public String ID;
	/* TYPE == 0 ? 5% discount : free product */
	public int TYPE;
	public String costumer;
	public int used = 0;
	public String message;
	
	public Voucher(String ID, int Type) {
		this.ID = ID;
		this.TYPE = Type;
	}


	public Voucher(String ID, int type, String userID, int used) {
		// TODO Auto-generated constructor stub
		this.ID = ID;
		this.TYPE = type;
		this.costumer = userID;
		this.used = used;
	}
}
