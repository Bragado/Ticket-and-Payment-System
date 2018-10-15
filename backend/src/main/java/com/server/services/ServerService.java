package com.server.services;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.json.JSONArray;
 

public interface ServerService {
	
	/*public Session login(String username, String password, String addr) throws SQLException, NoSuchAlgorithmException;
	
	String getUsernameFromSession(String userToken) throws SQLException;
	
	int getUserIDFromSesion(String userToken) throws SQLException;
	
	public boolean logout(String userToken, String string);
	
	public JSONArray searchFile(String query, String type, String ln1, String ln2) throws SQLException;
	public JSONArray searchUserFiles(String user) throws SQLException;
	
	boolean validate(String userCode);
	
	public boolean uploadFile(String Filename, String MimeType, int Length, int userID) throws SQLException;
	
	public  byte[] getPublicKeyFromUsername(String parameter) throws SQLException ;
	
	public byte[] getPublicKeyFromID(int id2) throws SQLException;*/
	
	public String Register(String name, String username, String password, String publicKey, int NIF); 
	
	
}
