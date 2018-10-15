package com.server.services;

 

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.server.utilis.Utilis;
 

public class DBService extends DBHelper implements ServerService {
		
	public static final String REGISTER_USER_QUERY = "insert into COSTUMER(NAME, USERNAME, PASSWORD, NIF, PUBLICKEY, USERID) values(?,?,?,?, ?, ?)";
	public static final String CHECK_IF_USER_EXISTS_QUERY ="";
	
	public DBService() throws ClassNotFoundException, SQLException {
		super();
	}

	

	public String Register(String name, String username, String password, String publicKey, int NIF) {
		
		UUID uuid = UUID.randomUUID();
    	String randomUUIDString = uuid.toString();
	
		try {
			Connection con = connection = DriverManager.getConnection(Utilis.JDBC_URL);
			
			if(userExists(username, publicKey)) {
				return "";
			}
			
			System.out.println("pass: " + Utilis.applySHA(password));
            System.out.println("name: " + name);
            
            PreparedStatement stmt = con.prepareStatement(REGISTER_USER_QUERY);
            stmt.setString(1, name);
            stmt.setString(2, username);
            stmt.setString(3, Utilis.applySHA(password));
            stmt.setInt(4, NIF);
            stmt.setString(5, publicKey);
            stmt.setString(6, randomUUIDString);
            stmt.execute();

             
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
		
		return randomUUIDString;
	}
	
 
	/**
	 * Checks if user exists
	 * @param username
	 * @param password
	 * @return true if user exists, false otherwise
	 */
	public boolean userExists(String username, String password) 
    {
		Connection con;
		int id = -1;
		try {
			con = DriverManager.getConnection(Utilis.JDBC_URL);
			
			PreparedStatement stmt = con.prepareStatement(CHECK_IF_USER_EXISTS_QUERY);
            stmt.setString(1, username);
            stmt.setString(2, Utilis.applySHA(password));
            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                id = rs.getInt("ID");
                System.out.println("ID: " + id);
            }
            
            con.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

       
        try{
        	System.out.println("pass: " + Utilis.applySHA(password));
            System.out.println("name: " + username);
        	
        }
        catch(Exception e){
        	e.printStackTrace();
            System.out.println("AUTHENTICATION FAILED");
        }
        
        
        return id > -1 ? true : false;

        
    }


 
	


}
