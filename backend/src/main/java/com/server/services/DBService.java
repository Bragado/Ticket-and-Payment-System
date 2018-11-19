package com.server.services;

 

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.server.utilis.CafeteriaOrderAnswer;
import com.server.utilis.Costumer;
import com.server.utilis.Event;
import com.server.utilis.Ticket;
import com.server.utilis.Utilis;
import com.server.utilis.Voucher;
 

public class DBService extends DBHelper implements ServerService {
		
	public static final String REGISTER_USER_QUERY = "insert into COSTUMER(NAME, USERNAME, PASSWORD, NIF, PUBLICKEY, USERID) values(?,?,?,?, ?, ?)";
	public static final String CHECK_IF_USER_EXISTS_QUERY ="";
	
	public DBService() throws ClassNotFoundException, SQLException, PropertyVetoException {
		super();
	}

	

	public String Register(String name, String username, String password, String publicKey, int NIF) throws SQLException {
		
		UUID uuid = UUID.randomUUID();
    	String randomUUIDString = uuid.toString();
	
    	if(!InsertNewUser(name, username, password, publicKey, NIF, randomUUIDString))
    		return "";
		
		return randomUUIDString;
	}
	
	public boolean AddCreditCard(String UUID, int Type, String Number, java.util.Date experationDate) throws SQLException {
		
		if(!checkUserIDExists(UUID))
			return false;
		
		int ID = insertCreditCard(Type, Number, new java.sql.Date(experationDate.getTime()));
		if(ID < 0)
			return false;
		if(!updateCreditCard(UUID, ID))
			return false;
		
		
		return true;
	}



	public boolean createEvent(String filename, java.util.Date date, String title, String description, String capacity, String ticketPrice)
			throws SQLException {
		
		return insertEvent(title, filename, description, new java.sql.Date(date.getTime()), Integer.parseInt(capacity), Integer.parseInt(ticketPrice));
	}
 
	
	public JSONObject getAvailableEvents() throws SQLException, JSONException {
		JSONObject events = new JSONObject();
		JSONArray eventslist = new JSONArray();
		ArrayList<Event> ev = getEvents();
		
		for(Event e : ev) {
			 	
			
			JSONObject obj = new JSONObject();
			
			
			obj.put("ID", e.ID);
			obj.put("Title", e.Title);
			obj.put("Description", e.Description);
			obj.put("Capacity", e.Capacity);
			obj.put("Date", e.date);
			obj.put("TicketPrice", e.ticketPrice) ;
			
			eventslist.put(obj);
		}
		
		events.put("List", eventslist);
		return events;
	}



	public JSONObject sellClientTickets(String eventID, String userID, String amount) throws SQLException, JSONException {
		
 
		
		int amountI = Integer.parseInt(amount);
		int neventID = Integer.parseInt(eventID);
		
		Event e =  super.getEvent(neventID) ; 
		if(!checkParametersforSellingTickets(e, userID, amountI))
			return null;
		 
		/* Update  event left capacity*/
		super.updateEventCapacity(e.Capacity - amountI, neventID);
 
		String[] places = Utilis.getRandomPlaces(amountI);
		String[] ticketID = new String[amountI];
 
		JSONObject resp = new JSONObject();
		resp.put("numberOfTickets", amountI);
		resp.put("date", e.date);
		resp.put("title", e.Title);
		
		JSONArray tickets = new JSONArray();
		JSONArray vouchers = new JSONArray();
		
		for(int i = 0; i < amountI; i++) {
			
			/* GENERATE TICKET */
			String tickeId = UUID.randomUUID().toString();
			ticketID[i] = tickeId;
			 
			// this can be done in another thread
			super.createTicket(tickeId, neventID, userID, places[i]);
			JSONObject ticket = new JSONObject();
			ticket.put("ID", tickeId);
			ticket.put("Place", places[i]);
			tickets.put(ticket);
			 
			/* GENERATE A VOUCHER */
			Voucher v = super.generateVoucherToClient(userID);
			JSONObject voucher = new JSONObject();
			voucher.put("ID", v.ID);
			voucher.put("TYPE", v.TYPE);
			vouchers.put(voucher);
		}
		int moneySpent = amountI*(e.ticketPrice);
		
		
		 
		
		
		double amountPending =   super.getAmountPending(userID) ;
		
		int totalMoney = moneySpent + (int)amountPending;
		int numberOfVouchers = totalMoney/100;
		int restOfMoney = totalMoney % 100;
		
		/*System.out.println("amountPending: " + amountPending);
		System.out.println("totalMoney: " + totalMoney);
		System.out.println("numberOfVouchers: " + numberOfVouchers);
		System.out.println("rest of money: " + restOfMoney);*/
	 
		for(int i = 0 ; i < numberOfVouchers; i++) {
			Voucher v = new Voucher(UUID.randomUUID().toString(), 0);
			
			// this can be done in another thread
			super.addVoucherToClient(v, userID);
			
			
			JSONObject voucher = new JSONObject();
			voucher.put("ID", v.ID);
			voucher.put("TYPE", v.TYPE);
			vouchers.put(voucher);
		}					
	 
		// this can be done in another thread
		super.updateVoucherManager(restOfMoney, userID);
		
		
		resp.put("tickets", tickets);
		resp.put("vouchers", vouchers);
		resp.put("price", moneySpent);
		return resp;
	}

	private boolean checkParametersforSellingTickets(Event e,   String userID, int amount) throws SQLException {
		if(amount < 1)
			return false;
		
		
		if(e == null)
			return false;
		boolean userExists =  checkUserIDExists(userID) ;
		if(! userExists)
			return false;
		
		
		
		int eventLeftCapacity = e.Capacity;
		
		if(eventLeftCapacity < amount)
			return false;
		
		return true;
	}



	public boolean valideTicket(String ticketID, String userID, String eventID) throws SQLException {
		Ticket ticket = super.getTicket(ticketID, userID);
		
		if(ticket == null)
			return false;
		if(ticket.used > 0)
			return false;
		
		int eventId = Integer.parseInt(eventID);
		if(ticket.eventID != eventId)
			return false;
		
		super.setTicketUsed(ticketID, userID);
		
		
		return true;
	}
	
	public Costumer getUserInfo(String id) throws SQLException {
		
		
		
		return super.getCostumerInfo(id);
	}



	public CafeteriaOrderAnswer registerCafeteriaOrder(String userID, JSONArray products, JSONArray vouchers  ) throws Exception {
		
		Double price = 0.0;
		Voucher[] requestedVouchers = null;
		Voucher[]  allowedVouchers = null;
		if(vouchers != null) {
			requestedVouchers = super.getVouchers(userID, vouchers);
			if (requestedVouchers != null)
				System.out.println("Requested Vouchers: " + requestedVouchers.length);
			allowedVouchers = verifyVouchers(requestedVouchers);
		}
		//System.out.println("Allowed Vouchers: " + allowedVouchers.length);

		if (allowedVouchers != null)
			System.out.println("voucher size: " + allowedVouchers.length);
		
		int IdOrder = -1;
		
		if((IdOrder = super.createCafeteriaOrder(userID, products)) < 0) {
			throw new Exception("can't continue the operation whitout destroing database integrity");
		}
		
		
		Map<Integer, Integer> p = getMapFromProducts(products, allowedVouchers);
		
		price = calcFinalPrice(p, allowedVouchers);
		
		System.out.println("price: "+  price);
		if(allowedVouchers != null)
			super.registerUsedVouchers(userID, allowedVouchers);
		super.updateFinalPrice(IdOrder, price);

		if (allowedVouchers != null)
			System.out.println("voucher size: " + allowedVouchers.length);
		return new CafeteriaOrderAnswer(price, allowedVouchers);
	}
	
	
	private Double calcFinalPrice(Map<Integer, Integer> p, Voucher[] allowedVouchers) throws SQLException {
		// TODO Auto-generated method stub
		Double price = 0.0;
		Iterator it = p.entrySet().iterator();
		int type;
		int quantity;
		double productValue;
		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        type = (Integer)pair.getKey();
	        quantity = (Integer)pair.getValue();
	        
	        productValue = super.getProductValue(type);
	        price += productValue*quantity;
	        
	        System.out.println("Type: " + type +"\tQuantity: " + quantity + "\tPrice: " + productValue +"\ttotal: " + price );
	        
	        
	    }
		if(allowedVouchers == null)
			return price;
		
		for(int i = 0; i < allowedVouchers.length; i++) {
			if(allowedVouchers[i].TYPE == 0)
				price *= 0.95;
		}
		
		return price;
	}



	private Map<Integer, Integer> getMapFromProducts(JSONArray products, Voucher[] allowedVouchers) throws JSONException {
		// TODO Auto-generated method stub
		Map<Integer, Integer>  prod= new HashMap<Integer, Integer>();
		JSONObject product;
		for(int i = 0; i < products.length(); i++) {
			product = products.getJSONObject(i);
			int type = product.getInt("type");
			int quantity = product.getInt("quantity");
			prod.put(type, quantity); 
		}	
		if(allowedVouchers == null)
			return prod;
		
		
		for(int i = 0; i< allowedVouchers.length; i++) {
			int id = allowedVouchers[i].TYPE;
			if(id > 0 ) {
				if(prod.get(id) > 0) {
					int quantity = prod.get(id);
					prod.put(id, quantity - 1);
				}else { // voucher not valid
					Utilis.removeElement(allowedVouchers, i);
					i--;
				}
				
				
			}
		}
		
		
		return prod;
	}



	private Voucher[] verifyVouchers(Voucher[] vouchers) {
		if (vouchers != null){
			if(vouchers.length < 2)
				return vouchers;

			if(vouchers.length == 2 && vouchers[0].TYPE == 0 && vouchers[1].TYPE == 0) {
				Voucher[] v = new Voucher[1];
				v[0] = vouchers[0];
				return v;
			}
		}

		return vouchers;
	}



	public String[] getVouchersName(Voucher[] vouchersAccepted) throws SQLException {
		String[] ret = new String[vouchersAccepted.length];
		for(int i = 0; i< vouchersAccepted.length; i++) {
			if(vouchersAccepted[i].TYPE == 0) {
				ret[i] = "5% discount";
			}else {
				String product = super.getVoucherProduct(vouchersAccepted[i].TYPE);
				ret[i] = "Free " + product;
			}
		}
		
		
		return ret;
	}



	 
	


}
