package com.server.utilis;

public class CafeteriaOrderAnswer {
	public Double price;
	public Voucher[] vouchers;
	
	public CafeteriaOrderAnswer(Double price, Voucher[] vouchers) {
		this.price = price;
		this.vouchers = vouchers;
	}
}
