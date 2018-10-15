package com.server.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

import com.server.services.DBService;
import com.server.utilis.Utilis;

public class AddCreditCard  extends HttpServlet{
private DBService database;		
	
	public AddCreditCard(DBService database) {
		this.database = database;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)		// TODO: change to doPost
			throws ServletException, IOException {
	 
		
	 
		
		
	}
	
}
