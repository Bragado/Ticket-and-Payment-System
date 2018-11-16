package com.server.services;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.server.utilis.CafeteriaOrderAnswer;
import com.server.utilis.Costumer;
import com.server.utilis.Voucher;
 

public interface ServerService {
	
	 
	
	public String Register(String name, String username, String password, String publicKey, int NIF) throws SQLException; 
	
	public boolean AddCreditCard(String UUID, int Type, String Number, java.util.Date  experationDate) throws SQLException;

	public boolean createEvent(String filename, java.util.Date date, String title, String description, String capacity,  String ticketPrice) throws SQLException;
	
	public JSONObject getAvailableEvents() throws SQLException, JSONException;

	public JSONObject sellClientTickets(String eventID, String userID, String amount) throws SQLException, JSONException;

	public boolean valideTicket(String ticketID, String userID, String eventID) throws SQLException ;

	public Costumer getUserInfo(String id) throws SQLException;

	public CafeteriaOrderAnswer registerCafeteriaOrder(String userID, JSONArray products, JSONArray vouchers  ) throws Exception;

	public String[] getVouchersName(Voucher[] vouchersAccepted) throws SQLException;
}
