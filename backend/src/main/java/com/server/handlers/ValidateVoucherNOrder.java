package com.server.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.server.services.DBService;
import com.server.services.ServerService;
import com.server.utilis.CafeteriaOrderAnswer;
import com.server.utilis.Voucher;
 




public class ValidateVoucherNOrder extends HttpServlet{

	private ServerService database;		
	
	public ValidateVoucherNOrder(ServerService database) {
		this.database = database;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)		// TODO: change to doPost
			throws ServletException, IOException {
		 
		/* Json Parse Request , part 1 */	
		  StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = req.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }
		  JSONObject jsonObject ;
		  System.out.println(jb.toString());
		  
		  try {
			/* Json Parse Request , part 2*/
			jsonObject =  new JSONObject(jb.toString());
			System.out.println("Aqui" );	
		    String userID = jsonObject.getString("userID");
		    System.out.println("userID : " + userID);
		    String signedUserID = jsonObject.getString("signedUserID");
		    System.out.println("signedUserID : " + signedUserID);
		    JSONArray products = jsonObject.getJSONArray("products");
		    JSONArray vouchers = jsonObject.getJSONArray("vouchers");
		   
		   System.out.println("length: " + vouchers.length());
		    
		    /* Validate User */
/*		    if(!database.valideUser(userID, signedUserID)) {
		    	resp.setStatus(HttpStatus.NOT_ACCEPTABLE_406);
		    	return;
		    }*/
		    	
		    
		    /* Operations */
		    Double price = 0.0; 
		    try {
				
				CafeteriaOrderAnswer cf = database.registerCafeteriaOrder(userID, products, vouchers.length() > 0 ? vouchers : null);
				price = cf.price;
				Voucher[] vouchersAccepted = cf.vouchers;
				JSONArray vouchersA = new JSONArray();
				if(vouchersAccepted != null) {
					String[] vs = database.getVouchersName(vouchersAccepted);
					for(int i = 0; i < vs.length; i++)
						vouchersA.put(vs[i]);

					System.out.println("voucher size: " + vouchersAccepted.length);
				}

				PrintWriter out = resp.getWriter();
				JSONObject obj = new JSONObject();
	            obj.put("price", price);
	            obj.put("vouchers", vouchersA);
	            out.println(obj.toString());
				
		    
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		   
		   
		    /* Answer */
		/*    resp.setStatus(HttpStatus.OK_200);
		    PrintWriter out = resp.getWriter();
            JSONObject obj = new JSONObject();
            obj.put("price", price);
            obj.put("valideVouchers", getValidVouchers(vouchersAccepted));
            out.println(obj.toString());*/
		    
		  } catch (JSONException e) {
		    // crash and burn
		     
		    resp.setStatus(HttpStatus.BAD_REQUEST_400);
		  }
		
		  
		  
		  
		

          
		  
		// Work with the data using methods like...
		  // int someInt = jsonObject.getInt("intParamName");
		  // String someString = jsonObject.getString("stringParamName");
		  // JSONObject nestedObj = jsonObject.getJSONObject("nestedObjName");
		  // JSONArray arr = jsonObject.getJSONArray("arrayParamName");
		  // etc...
		  
		
	}
		
}
