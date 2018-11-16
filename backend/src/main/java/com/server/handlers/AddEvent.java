package com.server.handlers;

 
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;

import com.server.services.ServerService;
import com.server.utilis.Utilis;







@MultipartConfig
public class AddEvent extends HttpServlet {
	
	private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement("/");
	
	private ServerService database;	
	public AddEvent(ServerService database) {
		this.database = database;
	}
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)		// TODO: change to doPost
			throws ServletException, IOException {
		//resp.setHeader("Access-Control-Allow-Origin", "*");
		 
		
		 String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/")){
	           	req.setAttribute(Request.__MULTIPART_CONFIG_ELEMENT, MULTI_PART_CONFIG);
	           
	           	if(req.getParameterMap().containsKey("title") && 
	    				req.getParameterMap().containsKey("description") &&
	    				req.getParameterMap().containsKey("capacity") && 
	    				req.getParameterMap().containsKey("ticketPrice") &&
	    				req.getParameterMap().containsKey("date")) {
	           		
	           		Part file = req.getPart("photo");
					String filename = Utilis.saveImage(file);
	           		String date = req.getParameter("date");
	           		String title = req.getParameter("title");
	           		String description = req.getParameter("description");
	           		String capacity = req.getParameter("capacity");
	           		String ticketPrice = req.getParameter("ticketPrice");
	           		Date eventDate = null;
	    			try {
	    				eventDate = Utilis.formatter.parse(date);
	    			} catch (ParseException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    				resp.setStatus(HttpStatus.BAD_REQUEST_400);
	    			}
	           		
	           		
	           		/* TODO: ERASE*/
	           		System.out.println("filename: " + filename);
	           		System.out.println("date: " + date);
	           		System.out.println("title: " + title);
	           		System.out.println("description: " + description);
	           		System.out.println("capacity: " + capacity);
	           		
	           		try {
						database.createEvent(filename, eventDate, title, description, capacity, ticketPrice);
					} catch (SQLException e) {
						resp.setStatus(HttpStatus.BAD_REQUEST_400);
					}
	           		resp.setStatus(HttpStatus.OK_200);
	           	}else {
	           		resp.setStatus(HttpStatus.BAD_REQUEST_400);
	           	}
	    }else {
			resp.setStatus(HttpStatus.BAD_REQUEST_400);
		}		
	}

} 
