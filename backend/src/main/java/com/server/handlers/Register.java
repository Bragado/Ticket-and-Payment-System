package com.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import com.server.services.DBService;
import com.server.services.ServerService;
import com.server.utilis.Utilis;


public class Register extends HttpServlet {

	// TODO encrypt password
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1249226876927897434L;
	private ServerService database;		
	
	public Register(ServerService database) {
		this.database = database;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)		// TODO: change to doPost
			throws ServletException, IOException {
		
		boolean operationSuccessufull = true;
		
		/*
		 * name 
		 *  nif 
		 *  username 
		 *  password; 
		 *  publicKey
		 * 
		 * 
	*/	 
		if(req.getParameterMap().containsKey("username") && req.getParameterMap().containsKey("password") &&
				req.getParameterMap().containsKey("name") && 
				req.getParameterMap().containsKey("publicKey")) {
			
			
			String username = req.getParameter("username");
			String pass = req.getParameter("password");
			String name = req.getParameter("name");
			int NIF = Integer.parseInt(req.getParameter("nif"));
			String publicKey = req.getParameter("publicKey");
			
			String res  = database.Register(name, username, pass, publicKey, NIF);
			 

			
			if(!res.equals("")) {
		             
                resp.setStatus(HttpStatus.OK_200);
                PrintWriter out = resp.getWriter();
                
                JSONObject obj = new JSONObject();

                try {
					obj.put("userID", res);
					//obj.put("PRIVATE_KEY", res.key.getEncoded());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               
                out.println(obj.toString());
                }else
				operationSuccessufull = false;
			}else
			operationSuccessufull= false;
		
		
		if(!operationSuccessufull){
			resp.setStatus(HttpStatus.BAD_REQUEST_400);
			 
		}
		 
		
	}
}
