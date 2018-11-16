package com.server.handlers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

import com.server.services.ServerService;
import com.server.utilis.Utilis;

public class GetImage extends HttpServlet {
	ServerService database;
	
	public GetImage(ServerService database) {
		this.database = database;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)		// TODO: change to doPost
			throws ServletException, IOException {
		
		if(req.getParameterMap().containsKey("photoName")) {
			
			resp.setContentType("image/png");
			
			String filename = req.getParameter("photoName"); 
			
			
			File f = new File(Utilis.getImgFolder() + "/" + filename);
			
			
			
			System.out.println("Size: " + f.length());
			
			BufferedImage bi = ImageIO.read(f);
			if(bi != null)
			System.out.println("Size: " + bi.getWidth());
			OutputStream out = resp.getOutputStream();
			ImageIO.write(bi, "png", out);
			out.close();
		}else
		resp.setStatus(HttpStatus.BAD_REQUEST_400);
		
	}
	
	private String getImageType(File file) throws IOException {
		String format = "";
		ImageInputStream iis = ImageIO.createImageInputStream(file);
		Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
		if (!iter.hasNext()) {
			throw new RuntimeException("No readers found!");
		}
		ImageReader reader = iter.next();
		format = reader.getFormatName();
		iis.close();
		return format;		
	}
	
	
}
