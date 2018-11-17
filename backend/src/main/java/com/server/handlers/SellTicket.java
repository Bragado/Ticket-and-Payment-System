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

public class SellTicket extends HttpServlet {

	ServerService database;
	
	public SellTicket(ServerService database) {
		this.database = database;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)		// TODO: change to doPost
			throws ServletException, IOException {
		
		if(req.getParameterMap().containsKey("eventID") && 
				req.getParameterMap().containsKey("userID") &&
				req.getParameterMap().containsKey("amount")) {
		
			String userID = req.getParameter("userID");
			String eventID = req.getParameter("eventID");
			String amount = req.getParameter("amount");
			
			try {
				JSONObject obj = database.sellClientTickets(eventID, userID, amount); 
				
				if(obj != null) {
					resp.setStatus(HttpStatus.ACCEPTED_202);
					PrintWriter out = resp.getWriter();
					out.println(obj.toString());
				}else {	// event is already full or capacity left event is smaller than the available tickets
					resp.setStatus(HttpStatus.PRECONDITION_FAILED_412);
				}
			} catch (SQLException e) {
				 
				e.printStackTrace();
				resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
			} catch (JSONException e) {
				 
				e.printStackTrace();
				resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
			}
			
			
			
			
			
		}else {
			resp.setStatus(HttpStatus.BAD_REQUEST_400);
		}
		
		
	}
	
	

}
