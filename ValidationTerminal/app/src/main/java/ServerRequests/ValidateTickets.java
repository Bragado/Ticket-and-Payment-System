package ServerRequests;

import android.util.Log;
import java.net.HttpURLConnection;
import java.net.URL;


import Logic.Ticket;

public class ValidateTickets implements Runnable{

    private String path = "/validate_ticket";
    private Ticket ticket;

    public ValidateTickets (Ticket ticket) {
        this.ticket = ticket;
    }


    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection = null;
        try{
            url = new URL(buildURL());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // get response
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200) {
                ticket.setStatus(Ticket.VALID);
                Log.d("validateticket", "Success");
                Log.d("validateticket", "response code: " + responseCode);
            } else if (responseCode == 406){
                ticket.setStatus(Ticket.REJECTED);
                Log.d("validateticket", "Ticket rejected");
                Log.d("validateticket", "response code:" + responseCode);
            } else {
                Log.d("validateticket", "error: " + responseCode);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private String buildURL(){
        String url = ServerUtils.server_path + path + "?ticketID=" + ticket.getId();
        url += "&userID=" + ticket.getUserId();
        url += "&eventID=" + ticket.getEventId();

        return url;
    }
}
