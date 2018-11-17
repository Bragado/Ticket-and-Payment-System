package com.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import com.server.services.ServerService;


public class Register extends HttpServlet {
 
	 
	private ServerService database;		
	
	public Register(ServerService database) {
		this.database = database;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)		// TODO: change to doPost
			throws ServletException, IOException {
		
		boolean operationSuccessufull = true;
		System.out.println("Received Register");
		String currentpath = System.getProperty("user.dir");
		System.out.println("current path is:" + currentpath);
 
		// 1 
		if(req.getParameterMap().containsKey("username") && 
				req.getParameterMap().containsKey("password") &&
				req.getParameterMap().containsKey("name") && 
				req.getParameterMap().containsKey("publicKey") &&
				req.getParameterMap().containsKey("nif")) {
			
			
			String username = req.getParameter("username");
			String pass = req.getParameter("password");
			String name = req.getParameter("name");
			int NIF = Integer.parseInt(req.getParameter("nif"));
			String publicKey = req.getParameter("publicKey");
			if(database == null)
				System.out.println("A base de dados esta mal");
			
			
			String res = "";
			try {
				res = database.Register(name, username, pass, publicKey, NIF);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
				return;
			}
			 

			 
			if(!res.equals("")) {
		             
                resp.setStatus(HttpStatus.OK_200);
                PrintWriter out = resp.getWriter();
                
                JSONObject obj = new JSONObject();

                try {
					obj.put("userID", res);
					 
				} catch (JSONException e) {
					 
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