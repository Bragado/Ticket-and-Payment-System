package com.server.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

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
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)		// TODO: change to doPost
			throws ServletException, IOException {
		System.out.println("Add credit Card");
		if(req.getParameterMap().containsKey("type") && 
				req.getParameterMap().containsKey("number") &&
				req.getParameterMap().containsKey("UUID") && 
				req.getParameterMap().containsKey("validity")) {
			
			 
			int Type = Integer.parseInt(req.getParameter("type"));
			String Number = req.getParameter("number");
			String dateS = req.getParameter("validity"); 
			Date experationDate = null;
			try {
				experationDate = Utilis.formatter.parse(dateS);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String UUID = req.getParameter("UUID"); 
			
			
			boolean res = false;
			try {
				res = database.AddCreditCard(UUID, Type, Number, experationDate);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
				return;
			}
			 
			if(!res) {
				resp.setStatus(HttpStatus.FORBIDDEN_403);
			}else
				resp.setStatus(HttpStatus.OK_200);
			 
			
	 
		}else
		resp.setStatus(HttpStatus.BAD_REQUEST_400);
		
	}
	
}
