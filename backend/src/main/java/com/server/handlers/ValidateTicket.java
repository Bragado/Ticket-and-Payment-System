package com.server.handlers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

import com.server.services.ServerService;

public class ValidateTicket  extends HttpServlet{

	private ServerService database;		
	
	public ValidateTicket(ServerService database) {
		this.database = database;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)		// TODO: change to doPost
			throws ServletException, IOException {
		if(req.getParameterMap().containsKey("ticketID") && 
				req.getParameterMap().containsKey("userID") && req.getParameterMap().containsKey("eventID") ) {
			String userID = req.getParameter("userID");
			String ticketID = req.getParameter("ticketID");
			String eventID = req.getParameter("eventID");
			System.out.println("its here");
			try {
				if(database.valideTicket(ticketID, userID, eventID))
					resp.setStatus(HttpStatus.OK_200);
				else
					resp.setStatus(HttpStatus.NOT_ACCEPTABLE_406);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
			}
			
		}else {
			resp.setStatus(HttpStatus.BAD_REQUEST_400);
		}
		
		
	}

}
