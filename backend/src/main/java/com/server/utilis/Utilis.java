package com.server.utilis;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Utilis {
	
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
	
	/*			/TABLES				*/
	
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
	
	/*			/Functions			*/
}
