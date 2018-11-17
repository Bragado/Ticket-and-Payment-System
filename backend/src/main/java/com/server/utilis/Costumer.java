package com.server.utilis;


public class Costumer {
	
	public int ID;
	public String name;
	public String username;
	public String password;
	public int NIF;
	public CreditCard creditCard;
	
	public Costumer(String name, int nif) {
		this.NIF = nif;
		this.name = name;
		
	}
	
	
}