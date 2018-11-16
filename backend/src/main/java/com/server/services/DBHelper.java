package com.server.services;

import java.beans.PropertyVetoException;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Date;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.postgresql.Driver;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.server.utilis.*; 

public class DBHelper {
	/* DATA BASE  */ // TODO: Put this into a configuration file
	//String url = "jdbc:postgresql://fCEGk5Q6fnYEnpp8qAnFu-OitxJjM3Rq@pellefant.db.elephantsql.com:5432/mpxygmbz";
	String DBurl = "jdbc:postgresql://pellefant.db.elephantsql.com/mpxygmbz";
	String DBusername = "mpxygmbz";
	String DBpassword = "fCEGk5Q6fnYEnpp8qAnFu-OitxJjM3Rq";
	
	ComboPooledDataSource dataSource;
	/* Queries */
	public static final String REGISTER_USER_QUERY = "insert into COSTUMER(ID, NAME, USERNAME, PASSWORD, NIF, PUBLICKEY) values(?,?,?,?, ?, ?)";
	public static final String CHECK_IF_USER_EXISTS_QUERY ="select ID from COSTUMER where USERNAME=?";
	public static final String CHECK_IF_USERID_EXISTS_QUERY ="select * from COSTUMER where ID=?";
	public static final String SELECT_USERID_FROM_UUID_QUERY = "select ID from COSTUMER where USERID=?";
	public static final String ADD_CREDITCARD_QUERY = "insert into CREDITCARD(TYPE, NUMBER, VALIDITY) values (?, ?, ?) RETURNING ID";
	public static final String UPDATE_CREDITCARD_QUERY = "UPDATE costumer SET CREDITCARD = ? WHERE ID = ?";
	public static final String ADD_EVENT_QUERY = "insert into EVENT(PHOTONAME, TITLE, DESCRIPTION, DATE, CAPACITY, TICKETPRICE) values (?, ?, ?, ?, ?, ?)";
	public static final String GET_ALL_EVENTS_QUERY = "select * from event";
	public static final String GET_EVENT_QUERY = "select * from event where ID = ?";
	public static final String GET_EVENT_CAPACITY_TICKETPRICE_QUERY = "select CAPACITY, TICKETPRICE from event where ID = ?";
	public static final String UPDATE_EVENT_CAPACITY = "UPDATE EVENT SET CAPACITY = ? WHERE ID = ?";
	public static final String CREATE_TICKER_QUERY = "insert into TICKET (TICKETID, EVENTID, OWNERID, PLACE) values (?, ?, ?, ?)";
	public static final String NUMBER_OF_PRODUCTS = "select count(*) as nprod from product";
	public static final String GET_AMOUNT_PENDIND = "select AMOUNTPENDING from VOUCHERMANAGER where COSTUMERID = ?";
	public static final String ADD_VOUCHER_TO_CLIENT = "insert into VOUCHER (ID, TYPE, COSTUMERID) values (?, ?, ?)";
	public static final String UPDATE_AMOUNT_PENDING = "UPDATE VOUCHERMANAGER SET AMOUNTPENDING = ? where COSTUMERID = ?";
	public static final String SET_TICKET_USED = "UPDATE TICKET SET USED = 1 where TICKETID = ? AND OWNERID = ?";
	public static final String SET_VOUCHER_USED = "UPDATE VOUCHER SET USED = 1 where ID = ? AND COSTUMERID = ?";
	public static final String CHECK_USER_IS_IN_TABLE = "insert into VOUCHERMANAGER (COSTUMERID, AMOUNTPENDING) values (?, 0.0)";
	public static final String GET_TICKET = "select * from ticket where TICKETID = ? AND OWNERID = ?";
	public static final String GET_USER_INFO = "select * from costumer where ID = ?";
	public static final String GET_VOUCHER_INFO = "select * from voucher where ID = ? and COSTUMERID = ? and USED = 0";
	public static final String ADD_NEW_ORDER = "insert into CAFETARIAORDER (PRICE, DATE, COSTUMERID) values (0.0, ?, ?) RETURNING ID";
	public static final String ADD_NEW_PRODUCT_TO_ORDER = "insert into CAFETERIAORDERNPRODUCTS (IDPRODUCT, IDCAFETERIAORDER, QUANTITY) values (?, ?, ?)";
	public static final String GET_PRODUCT_PRICE= "select price from product where ID = ?";
	public static final String SET_ORDER_PRICE = "update CAFETARIAORDER set PRICE = ? where ID = ?";
	public static final String GET_PRODUCTNAME= "select NAME from PRODUCT WHERE PRODUCT.ID = ?";
	
	
	public DBHelper() throws ClassNotFoundException, SQLException, PropertyVetoException { // TODO: remove this throw
		try {
            Class.forName(Driver.class.getName().toString());
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
		dataSource = getDataSource();
	}
	
	 private ComboPooledDataSource getDataSource() throws PropertyVetoException
	 {
		 ComboPooledDataSource cpds = new ComboPooledDataSource();
		 cpds.setJdbcUrl(DBurl);
		 cpds.setUser(DBusername);
		 cpds.setPassword(DBpassword);
		 cpds.setInitialPoolSize(3);
		 cpds.setMinPoolSize(2);
		 cpds.setAcquireIncrement(1);
		 cpds.setMaxPoolSize(5);
		 cpds.setMaxStatements(100);
		 return cpds;
	 }

	
	
	
	
	
	
	protected boolean InsertNewUser(String name, String username, String password, String publicKey, int NIF, String UUID) throws SQLException {
		
		Connection con = dataSource.getConnection();
 
		if(con == null) { 
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return false;
		}
		try {
			if(CheckUserExists(username))
				return false;
			PreparedStatement stmt = con.prepareStatement(REGISTER_USER_QUERY);
			stmt.setString(1, UUID);
			stmt.setString(2, name);
            stmt.setString(3, username);
            stmt.setString(4, password);
            stmt.setInt(5, NIF);
            stmt.setString(6, publicKey);
            stmt.execute();
             con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("[ERROR-DB] INSERT USER QUERY ERROR");
			return false;
		}
		
		return true;
	}
	
	
	protected boolean CheckUserExists(String username) throws SQLException {
		
		Connection con = dataSource.getConnection();
		
		 
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return false;
		}
		
		PreparedStatement stmt = con.prepareStatement(CHECK_IF_USER_EXISTS_QUERY);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            return true;
        }
        
        con.close();
            
		
		return  false;
	}
	 
	protected boolean checkUserIDExists(String ID) throws SQLException {
		Connection con = dataSource.getConnection();
		boolean ret = false;
	 
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return false;
		}
		
		PreparedStatement stmt = con.prepareStatement(CHECK_IF_USERID_EXISTS_QUERY);
        stmt.setString(1, ID);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            ret = true;;
        }
        
        con.close();
            
		
		return  ret;
		
		
	}
	
	
	 
	protected int insertCreditCard( int Type, String Number, Date date) throws SQLException {
		Connection con = dataSource.getConnection();
		int ID = -1;
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return ID;
		}
		
		PreparedStatement stmt = con.prepareStatement(ADD_CREDITCARD_QUERY);
        stmt.setInt(1, Type);
        stmt.setString(2, Number);
        stmt.setDate(3, date);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            ID = rs.getInt("ID");
            System.out.println("ID: " + ID);
        }
        
        con.close();
		
		return ID;
	}
	
	protected boolean updateCreditCard(String userID, int ID) throws SQLException {
		Connection con = dataSource.getConnection();
		if(con == null)
			return false;
		
		PreparedStatement stmt = con.prepareStatement(UPDATE_CREDITCARD_QUERY);
		stmt.setString(2, userID);
		stmt.setInt(1, ID);
		stmt.execute();
        con.close();
		return true;
	}
	
	
	protected int getUserIDFromUUID(String UUID) throws SQLException {
		Connection con = dataSource.getConnection();
		
		int id = -1;
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return id;
		}
		
		PreparedStatement stmt = con.prepareStatement(SELECT_USERID_FROM_UUID_QUERY);
        stmt.setString(1, UUID);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            id = rs.getInt("ID");
            System.out.println("ID: " + id);
        }
        
        con.close();
            
		
		
		return id  ;
	}
	
	protected boolean insertEvent(String title, String filename, String description, Date date, int capacity, int ticketPrice) throws SQLException {
		
		Connection con = dataSource.getConnection();
		
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return false;
		}
		PreparedStatement stmt = con.prepareStatement(ADD_EVENT_QUERY);
		stmt.setString(1, filename);
		stmt.setString(2, title);
		stmt.setString(3, description);
		stmt.setDate(4, date);
		stmt.setInt(5, capacity);
		stmt.setInt(6, ticketPrice);
		stmt.execute();
		con.close();
		
		return true;
	}
	
	
	protected ArrayList<Event> getEvents() throws SQLException {
		
		ArrayList<Event> events = new ArrayList<Event>();
		
		Connection con = dataSource.getConnection();
		
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return null;
		}
		

		PreparedStatement stmt = con.prepareStatement(GET_ALL_EVENTS_QUERY);
        
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            int ID = rs.getInt("ID");
            String photoname = rs.getString("PHOTONAME");
            String title = rs.getString("TITLE");
            String description = rs.getString("DESCRIPTION");
            String date = rs.getString("DATE");
            int capacity = rs.getInt("CAPACITY");
            int ticketPrice = rs.getInt("TICKETPRICE");
            System.out.println("Ticket price: " + ticketPrice);
        	events.add(new Event(ID, photoname, title, description, date, capacity, ticketPrice));
        }
        
        con.close();
		
		
		return events;		
	}

/*	public int getEventCapacity(int eventID) throws SQLException {
		int capacity = -1;
		
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return capacity;
		}
		PreparedStatement stmt = con.prepareStatement(GET_EVENT_CAPACITY_QUERY);
		stmt.setInt(1, eventID);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			capacity = rs.getInt("CAPACITY");
		}
		con.close();
		return capacity;
	}
	*/
	public void updateEventCapacity(int capacity, int eventID) throws SQLException {
		 
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return;
		}
		 
		PreparedStatement stmt = con.prepareStatement(UPDATE_EVENT_CAPACITY);
		stmt.setInt(1, capacity);
		stmt.setInt(2, eventID);
		stmt.execute();
		 
		con.close();
		 
	}

	public boolean createTicket(String ticketId, int neventID, String userID, String place) throws SQLException {
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return false;
		}
		PreparedStatement stmt = con.prepareStatement(CREATE_TICKER_QUERY);
		stmt.setString(1, ticketId);
		stmt.setInt(2, neventID);
		stmt.setString(3, userID);
		stmt.setString(4, place);
		
		stmt.execute();
		con.close();

		return true;
	}

	public Event getEvent(int eventID) throws SQLException {
		// TODO Auto-generated method stub
		
		Event e = null;
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return null;
		}
		PreparedStatement stmt = con.prepareStatement(GET_EVENT_QUERY);
		stmt.setInt(1, eventID);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			e = new Event(rs.getString("TITLE"), rs.getDate("DATE").toString(), rs.getInt("CAPACITY"), rs.getInt("TICKETPRICE"));
			 
		}
		con.close();
		return e;
	}
	
	
	public int getNumberOfProducts() throws SQLException {
		int n = 4;	
		
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return 0;
		}
		PreparedStatement stmt = con.prepareStatement(NUMBER_OF_PRODUCTS);
		
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			n = rs.getInt("nprod"); 
		}
		con.close();
		return n;
		
	}

	public Voucher generateVoucherToClient(String userID) throws SQLException {
		// TODO Auto-generated method stub
		int numberOfProducts = 4 /*getNumberOfProducts()*/;
		int product = (int) (Math.random() * numberOfProducts + 1);
		Voucher v = new Voucher(UUID.randomUUID().toString(), product);
		// this can be done in another thread
		addVoucherToClient(v, userID);  
		
		
		return v; 
	}

	public double getAmountPending(String userID) throws SQLException {
		
		double n = 0.0;
		
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return 0;
		}
		PreparedStatement stmt = con.prepareStatement(GET_AMOUNT_PENDIND);
		stmt.setString(1, userID);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			n = rs.getDouble("AMOUNTPENDING"); 
		}
		con.close();
		 
		
		return n;
	}

	public void addVoucherToClient(Voucher v, String userID) throws SQLException {
		// TODO Auto-generated method stub
		
		
	 	Connection con = dataSource.getConnection();
		int ID = -1;
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return ;
		}
		
		PreparedStatement stmt = con.prepareStatement(ADD_VOUCHER_TO_CLIENT);
        stmt.setInt(2, v.TYPE);
        stmt.setString(1, v.ID);
        stmt.setString(3, userID);
        stmt.execute();
        con.close(); 
		
	}

	public void updateVoucherManager(double restOfMoney, String userID) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return;
		}
		
		PreparedStatement stmt = con.prepareStatement(CHECK_USER_IS_IN_TABLE);
		stmt.setString(1, userID);
		try {
			stmt.execute();
		}catch(SQLException e ) {
 
		}
		 
		
		stmt = con.prepareStatement(UPDATE_AMOUNT_PENDING);
		stmt.setDouble(1, restOfMoney);
		stmt.setString(2, userID);
		stmt.execute();
		con.close();
	}

	public  Ticket getTicket(String ticketID, String userID) throws SQLException {
		
		Ticket t = null;
		
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return null;
		}
		PreparedStatement stmt = con.prepareStatement(GET_TICKET);
		stmt.setString(1, ticketID);
		stmt.setString(2, userID);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			t = new Ticket(rs.getString("TICKETID"), rs.getString("OWNERID"), rs.getInt("EVENTID"), rs.getInt("USED"));
			
		}
		con.close();
		 
		
		return t;
	}

	public void setTicketUsed(String ticketID, String userID) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return;
		}
		
		PreparedStatement stmt = con.prepareStatement(SET_TICKET_USED);
		stmt.setString(1, ticketID);
		stmt.setString(2, userID);
		stmt.execute();
		con.close();
	}

	public Costumer getCostumerInfo(String id) throws SQLException {
		Costumer c = null;
		
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return null;
		}
		PreparedStatement stmt = con.prepareStatement(GET_USER_INFO);
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			c = new Costumer(rs.getString("NAME"),  rs.getInt("NIF") );
			
		}
		con.close();
		
		return c;
	}

	public Voucher[] getVouchers(String userID, JSONArray vouchers) throws SQLException, JSONException {
		Voucher[] validVouchers = new Voucher[vouchers.length()];
		boolean validreturn = false;
		
		Connection con = dataSource.getConnection();
		
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return null;
		}
		
		PreparedStatement stmt = con.prepareStatement(GET_VOUCHER_INFO);
		ResultSet rs;
		Voucher v = null;
		for(int i = 0; i < vouchers.length(); i++) {
			String voucherID = vouchers.getString(i);
			stmt.setString(1, voucherID);
			stmt.setString(2, userID);
			rs = stmt.executeQuery();
			if(rs.next()) {
				System.out.println("voucherFound");
				v = new Voucher(rs.getString("ID"),  rs.getInt("TYPE"), rs.getString("COSTUMERID"), rs.getInt("USED"));
				
				validreturn = true;
				validVouchers[i] = v;
			}
		}
		
		con.close();
		return (validreturn ? validVouchers : null);
	}

	public int createCafeteriaOrder(String userID, JSONArray products) throws SQLException, JSONException {
		// TODO Auto-generated method stub
		int id = -1;
		
		 
		Date date = Utilis.getCurrentDate();
		id = insertNewOrder(date, userID);
		
		for(int i = 0; i < products.length(); i++) {
			JSONObject obj = products.getJSONObject(i);
			
			int prodcuctID = obj.getInt("type");
			int quantity = obj.getInt("quantity");
			registerProduct(prodcuctID, id, quantity);
			
		}
		
		
		return id;
	}
	
	
	private void registerProduct(int prodcuctID, int id, int quantity) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = dataSource.getConnection();
		 
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return;
		}
		PreparedStatement stmt = con.prepareStatement(ADD_NEW_PRODUCT_TO_ORDER);
		stmt.setInt(1, prodcuctID);
        stmt.setInt(2, id);
        stmt.setInt(3, quantity);
        stmt.execute();
		con.close();
		
		
	}

	public int insertNewOrder(Date date, String userID) throws SQLException {
		Connection con = dataSource.getConnection();
		int ID = -1;
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return ID;
		}
		
		PreparedStatement stmt = con.prepareStatement(ADD_NEW_ORDER);
		stmt.setDate(1, date);
        stmt.setString(2, userID);
        
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
        {
            ID = rs.getInt("ID");
            System.out.println("ID: " + ID);
        }
        
        con.close();
		
		return ID;
		
		
		
	}

	public void registerUsedVouchers(String userID, Voucher[] allowedVouchers) throws SQLException {
		 
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return;
		}
		
		
		
		PreparedStatement stmt = con.prepareStatement(SET_VOUCHER_USED);
		for(int i = 0; i< allowedVouchers.length; i++) {
			String voucherID = allowedVouchers[i].ID;
			stmt.setString(1, voucherID);
			stmt.setString(2, userID);
			stmt.execute();
		}
		
		
		
		con.close();
	}

	public double getProductValue(int type) throws SQLException {
		double price = 0;
		
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return price;
		}
		PreparedStatement stmt = con.prepareStatement(GET_PRODUCT_PRICE);
		stmt.setInt(1, type);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			price =rs.getDouble("PRICE");
			
		}
		con.close();
		
		return price;
	}

	public void updateFinalPrice(int idOrder, Double price) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return;
		}
		
		PreparedStatement stmt = con.prepareStatement(SET_ORDER_PRICE);
		stmt.setInt(2, idOrder);
		stmt.setDouble(1, price);
		stmt.execute();
		con.close();
	}

	public String getVoucherProduct(int type) throws SQLException {
		String name = "";
		
		Connection con = dataSource.getConnection();
		if(con == null) {
			System.out.println("[ERROR-DB] DATABASE CONNECTION NOT FOUND");
			return name;
		}
		PreparedStatement stmt = con.prepareStatement(GET_PRODUCTNAME);
		stmt.setInt(1, type);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			name =rs.getString("NAME");
			
		}
		con.close();
		
		return name;
	}
	
 	
	
	
	

	
	
	

}
