package com.server.services;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.server.utilis.*; 

public class DBHelper {
	
	
	
	
	
	public Connection connection;
	
	public DBHelper() throws ClassNotFoundException, SQLException { // TODO: remove this throw
		Class.forName(Utilis.DRIVER);
		connection = DriverManager.getConnection(Utilis.JDBC_URL);
		createTables();
				
		connection.close();
		
	}
	 

	private void createTables() throws SQLException {
		// table user
		Statement stmt = connection.createStatement();
		
		if(!isTableCreated(Utilis.COSTUMER_TABLE) ) {
			stmt.executeUpdate(createTableCostumer());
		}
		if(!isTableCreated(Utilis.CREDIT_CARD_TABLE)) {
			stmt.executeUpdate(createTableCreditCard());
		}
		if(!isTableCreated(Utilis.EVENT_TABLE)) {
			stmt.executeUpdate(createTableEvent());
		}
		if(!isTableCreated(Utilis.PRODUCT_TABLE)) {
			stmt.executeUpdate(createTableProduct());
		}
		if(!isTableCreated(Utilis.CAFETERIAORDER_TABLE)) {
			stmt.executeUpdate(createTableCafetariaOrder());
		}
		if(!isTableCreated(Utilis.CAFETERIAORDERNPRODUCTS_TABLE)) {
			stmt.executeUpdate(createTableCafeteriaOrderNProducts());
		}
		if(!isTableCreated(Utilis.CAFETARIAORDERNVOUCHER_TABLE)) {
			stmt.executeUpdate(createTableCafeteriaOrderNVoucher());
		}
		if(!isTableCreated(Utilis.VOUCHER_TABLE)) {
			stmt.executeUpdate(createTableVoucher());
		}
		if(!isTableCreated(Utilis.VOUCHERMANAGER_TABLE)) {
			stmt.executeUpdate(createTableVoucherManager());
		}
		stmt.close();
	}
	
	public boolean isTableCreated( String sTablename) throws SQLException{
	    if(connection!=null)
	    {
	        DatabaseMetaData dbmd = connection.getMetaData();
	        ResultSet rs = dbmd.getTables(null, null, sTablename.toUpperCase(),null);
	        if(rs.next())
	        {
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	        
	    }
	    return false;
	}
	
	
	private String createTableCostumer() {

		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append("CREATE TABLE Costumer (");
		strBuilder.append("ID INTEGER NOT NULL ");
		strBuilder.append("PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
		+ "(START WITH 1, INCREMENT BY 1),");
		strBuilder.append("USERNAME VARCHAR(50),");
		strBuilder.append("PASSWORD VARCHAR(256),");
		strBuilder.append("PUBLICKEY VARCHAR(256),");
		strBuilder.append("USERID VARCHAR(256),");
		strBuilder.append("NIF INTEGER NOT NULL,");
		strBuilder.append("CREDITCARD INTEGER, ");
		strBuilder.append("CONSTRAINT creditCard_fk FOREIGN KEY (CREDITCARD) REFERENCES CreditCard(ID))");
		return strBuilder.toString();
	}
	
	private String createTableCreditCard() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CREATE TABLE CreditCard (");
		strBuilder.append("ID INTEGER NOT NULL ");
		strBuilder.append("PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),");
		strBuilder.append("TYPE INTEGER NOT NULL, ");
		strBuilder.append("NUMBER INTEGER NOT NULL, ");
		strBuilder.append("VALIDITY DATE NOT NULL)");		
		return strBuilder.toString();
	}
	
	private String createTableEvent() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CREATE TABLE Event (");
		strBuilder.append("ID INTEGER NOT NULL ");
		strBuilder.append("PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),");
		strBuilder.append("PHOTONAME VARCHAR(256) NOT NULL, ");
		strBuilder.append("TITLE VARCHAR(64) NOT NULL, ");
		strBuilder.append("DESCRIPTION VARCHAR(1024) NOT NULL, ");
		strBuilder.append("DATE DATE NOT NULL, ");
		strBuilder.append("CAPACITY INTEGER NOT NULL )");
		return strBuilder.toString();		
	}
	
	public String createTableCafetariaOrder() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CREATE TABLE CAFETERIAORDER (");
		strBuilder.append("ID INTEGER NOT NULL ");
		strBuilder.append("PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),");
		strBuilder.append("FINALPRICE INTEGER NOT NULL, ");
		strBuilder.append("PRICE INTEGER NOT NULL, ");
		strBuilder.append("DATE DATE NOT NULL, ");
		strBuilder.append("COSTUMERID INTEGER NOT NULL, ");
		strBuilder.append("CONSTRAINT costumer_fk FOREIGN KEY (COSTUMERID) REFERENCES Costumer(ID))");
		return strBuilder.toString();
	}
	
	public String createTableCafeteriaOrderNProducts () {	// TODO: Constraints for foreign keys
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CREATE TABLE CAFETERIAORDERNPRODUCTS (");
		strBuilder.append("IDPRODUCT INTEGER NOT NULL, ");
		strBuilder.append("IDCAFETERIA INTEGER NOT NULL, ");
		strBuilder.append("QUANTITY INTEGER NOT NULL)");
		
		return strBuilder.toString();
	}
	
	public String createTableProduct() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("CREATE TABLE PRODUCT (");
		strBuilder.append("ID INTEGER NOT NULL ");
		strBuilder.append("PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),");
		strBuilder.append("NAME VARCHAR(64) NOT NULL, ");
		strBuilder.append("PRICE INTEGER NOT NULL)");
		
		return strBuilder.toString();
	}
	
	
	public String createTableCafeteriaOrderNVoucher() {		//TODO
		// IDCafeteriaOrder, IDVoucher
		StringBuilder strBuilder = new StringBuilder();
		return strBuilder.toString();
	}
	
	public String createTableVoucher() {		//TODO
		// ID, Discount, CostumerID
		StringBuilder strBuilder = new StringBuilder();
		return strBuilder.toString();
	}
	
	public String createTableVoucherManager() {		//TODO
		// CostumerID, AmountPending
		StringBuilder strBuilder = new StringBuilder();
		return strBuilder.toString();
	}
	
	
	
	
	

}
