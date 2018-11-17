package com.server.utilis;
 
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

 
import javax.servlet.http.Part;

 

public class Utilis {
	
	/* Global Variables*/
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	
	
	/*			TABLES				*/
	public static final String COSTUMER_TABLE = "Costumer";
	public static final String CREDIT_CARD_TABLE = "CreditCard";
	public static final String EVENT_TABLE = "Event";
	public static final String PRODUCT_TABLE = "PRODUCT";
	public static final String CAFETERIAORDER_TABLE = "CAFETERIAORDER";
	public static final String CAFETERIAORDERNPRODUCTS_TABLE = "CAFETERIAORDERNPRODUCTS";
	public static final String CAFETARIAORDERNVOUCHER_TABLE = "CafeteriaOrderNVoucher";
	public static final String VOUCHER_TABLE = "Voucher";
	public static final String VOUCHERMANAGER_TABLE = "VoucherManager";
	
	 
	
	/*			RESERVED WORDS		*/
	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL="jdbc:derby:APP;create=true";
	
	/*			/RESERVED WORDS		*/
	
	
	
	/*			Functions			*/
	public static String applySHA(String string)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
            String fileId2 = new String(hash, StandardCharsets.UTF_8);
            return String.format("%040x", new BigInteger(1, fileId2.getBytes(/*YOUR_CHARSET?*/)));
        }
        catch(Exception e)
        {
            System.out.println("Failed to apply SHA256");
        }

        return null;
    }
	
	public static String getImgFolder() {
		String currentpath = System.getProperty("user.dir") + "/Images";
		File file = new File(currentpath);
		if (!file.exists()) {
	        if (file.mkdir()) {
	            System.out.println("directory created successfully");
	        } else {
	            System.out.println("directory is not created");
	        }
	    }
		
		
		return currentpath;
	}

	public static String saveImage(Part file) throws IOException {
		// TODO Auto-generated method stub
		String filename = UUID.randomUUID().toString();
		file.write(Utilis.getImgFolder() + "/" + filename  );
		return filename;
	}

	public static String[] getRandomPlaces(int  amount) {
		
		String[] places = new String[amount];
		
		for(int i = 0; i < amount; i++) {
			int number = (int) (Math.random() * 50 + 1);
			places[i] = "" + number;
		}
		
		
		return places;
		
	}
	
	public static java.sql.Date getCurrentDate() {
		Date date = new Date();
		return new java.sql.Date(date.getTime());
	}
	
	public static void removeElement(Object[] arr, int removedIdx) {
	    System.arraycopy(arr, removedIdx + 1, arr, removedIdx, arr.length - 1 - removedIdx);
	}
  
	
	/*			/Functions			*/
}
