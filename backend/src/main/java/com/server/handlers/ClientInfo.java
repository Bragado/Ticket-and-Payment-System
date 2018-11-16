package com.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import com.server.services.ServerService;
import com.server.utilis.Costumer;

public class ClientInfo extends HttpServlet {
	
	private ServerService database;	
	public ClientInfo(ServerService database) {
		this.database = database;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)		 
			throws ServletException, IOException {
	
		if(req.getParameterMap().containsKey("userID")) {
			
			  String id = req.getParameter("userID");
			  
			  try {
				Costumer c = database.getUserInfo(id);
				PrintWriter out = resp.getWriter();
                
                JSONObject obj = new JSONObject();
                obj.put("name", c.name);
                obj.put("nif", c.NIF);
                out.println(obj.toString());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
			}
			
		}else {
			resp.setStatus(HttpStatus.BAD_REQUEST_400);
		}
	}

}
