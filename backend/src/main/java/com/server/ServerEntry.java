package com.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder; 
import com.server.services.DBService;

public class ServerEntry {

	public static final String HOME_PATH = "/";
	public static final String REGISTER_PATH = "/register";
	public static final String SELL_TICKET_PATH = "/sell_ticket";
	public static final String ADD_EVENT = "/add_event";
	public static final String ADD_CREDIT_CARD = "/add_credit_card";
	public static final String VALIDATE_TICKET = "/validate_ticket"; 
	public static final String VALIDATE_VOUCHER = "/validate_voucher";
	public static final String GET_TRANSACTIONS = "/getTrasanctions";
	private static final String GET_EVENTS = "/listEvents";
	private static final String GET_IMAGE_EVENT = "/getEventImage";
	private static final String CLIENT_INFO = "/clientInfo";
	
	// TODO: redefine variables values:
	public static final String KEYSTORE_PATH = "";
	private static final int OUTPUT_BUFFER_SIZE = 32768;
    private static final int REQUEST_HEADER_SIZE = 8192;
    private static final int RESPONSE_HEADER_SIZE = 8192;
    private static final String ALLOWED_METHODS = "GET,POST,DELETE,HEAD,OPTIONS";
    private static final String PATH_SPEC = "/*";

    private static final String NO_CONFIG_PROVIDED = "Please provide a config file as argument";
	
    
    public static final Integer Port = 7074;	// should be initialize with the parameters
	
	
	
	public static void main(String[] args) throws Exception {

		Server server = new Server(Port);				 
		
		DBService database = new DBService();
		//initializeDataBase(database);
		
		startHandlers(server, database);
		server.start();

	}
	
	public static Server createServer() {
		return null;
	}
	
	public static boolean startHandlers(Server server, DBService database) {
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handler.setContextPath(HOME_PATH);
		server.setHandler(handler);
		
		handler.addServlet(new ServletHolder(new com.server.handlers.Register(database)), REGISTER_PATH);
		handler.addServlet(new ServletHolder(new com.server.handlers.SellTicket(database)), SELL_TICKET_PATH);
		handler.addServlet(new ServletHolder(new com.server.handlers.AddEvent(database)), ADD_EVENT); 
		handler.addServlet(new ServletHolder(new com.server.handlers.GetTransactions(database)), GET_TRANSACTIONS);
		handler.addServlet(new ServletHolder(new com.server.handlers.ValidateVoucherNOrder(database)),VALIDATE_VOUCHER);
		handler.addServlet(new ServletHolder(new com.server.handlers.AddCreditCard(database)), ADD_CREDIT_CARD);
		handler.addServlet(new ServletHolder(new com.server.handlers.GetEvents(database)), GET_EVENTS);
		handler.addServlet(new ServletHolder(new com.server.handlers.GetImage(database)), GET_IMAGE_EVENT);
		handler.addServlet(new ServletHolder(new com.server.handlers.ValidateTicket(database)), VALIDATE_TICKET);
		handler.addServlet(new ServletHolder(new com.server.handlers.ClientInfo(database)), CLIENT_INFO); 
		
	/*	FilterHolder holder = new FilterHolder(new CrossOriginFilter());
        holder.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, ALLOWED_ORIGINS);
        holder.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, ALLOWED_METHODS);
        handler.addFilter(holder, PATH_SPEC, EnumSet.of(DispatcherType.REQUEST));*/
		
		return true;
	}
	
	
	

}
