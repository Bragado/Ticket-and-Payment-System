package com.server;

import java.io.FileNotFoundException;

import org.eclipse.jetty.server.Server;
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
	public static final String EMIT_VOUCHER = "/getVoucher";
	public static final String VALIDATE_VOUCHER = "/validate_voucher";
	public static final String GET_TRANSACTIONS = "/getTrasanctions";
	
	
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
		initializeDataBase(database);
		
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
		handler.addServlet(new ServletHolder(new com.server.handlers.EmitVoucher(database)), EMIT_VOUCHER);
		handler.addServlet(new ServletHolder(new com.server.handlers.GetTransactions(database)), GET_TRANSACTIONS);
		handler.addServlet(new ServletHolder(new com.server.handlers.ValidateVoucher(database)),VALIDATE_VOUCHER);
		handler.addServlet(new ServletHolder(new com.server.handlers.AddCreditCard(database)), ADD_CREDIT_CARD);
		//handler.addServlet(new ServletHolder(), pathSpec);
		//handler.addServlet(new ServletHolder(), pathSpec);
		
		 
		return true;
	}
	
	public static boolean initializeSLL(Server server) throws FileNotFoundException{
		/*File file = new File(KEYSTORE_PATH);
        if(!file.exists())
        {
            throw new FileNotFoundException(file.getAbsolutePath());
        }

        String path = file.getAbsolutePath();

        // HTTP Configuration
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme(HTTPS);
        http_config.setSecurePort(Port);
        http_config.setOutputBufferSize(OUTPUT_BUFFER_SIZE);
        http_config.setRequestHeaderSize(REQUEST_HEADER_SIZE);
        http_config.setResponseHeaderSize(RESPONSE_HEADER_SIZE);
        http_config.setSendServerVersion(true);
        http_config.setSendDateHeader(false);

        HttpConfiguration ssl_config = new HttpConfiguration(http_config);
        ssl_config.addCustomizer(new SecureRequestCustomizer(true));

        // SSL Context Factory
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(path);
        sslContextFactory.setKeyStorePassword(KEYSTORE_PASSWORD);
        sslContextFactory.setKeyManagerPassword(KEY_MANAGER_PASSWORD);
        sslContextFactory.setTrustStorePath(path);
        sslContextFactory.setTrustStorePassword(TRUSTSTORE_PASSWORD);
        sslContextFactory.setIncludeCipherSuites(CYPHER_SUITS_1, CYPHER_SUITS_2, CYPHER_SUITS_3);

        // SSL HTTP Configuration
        HttpConfiguration https_config = new HttpConfiguration(http_config);
        https_config.addCustomizer(new SecureRequestCustomizer());

        // SSL Connector
        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                new HttpConnectionFactory(ssl_config));
        sslConnector.setPort(Port);
        server.addConnector(sslConnector);*/
        return true;
	}
	
	public static boolean initializeDataBase(DBService database) {
		return true;
	}
	

}
