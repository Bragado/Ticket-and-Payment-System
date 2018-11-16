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

import com.server.services.DBService;

public class GetEvents extends HttpServlet{
	private DBService database;		
	
	public GetEvents(DBService database) {
		this.database = database;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)		 
			throws ServletException, IOException {
		 
		try {
			 JSONObject obj = database.getAvailableEvents();
			 PrintWriter out = resp.getWriter();
			 out.println(obj.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStatus(HttpStatus.BAD_REQUEST_400);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStatus(HttpStatus.BAD_REQUEST_400);
		} 
		
	}

}
