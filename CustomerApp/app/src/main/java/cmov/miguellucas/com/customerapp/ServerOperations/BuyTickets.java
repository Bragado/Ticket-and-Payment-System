package cmov.miguellucas.com.customerapp.ServerOperations;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import cmov.miguellucas.com.customerapp.Models.Ticket;
import cmov.miguellucas.com.customerapp.Models.Voucher;
import cmov.miguellucas.com.customerapp.Utils.ServerOps;

public class BuyTickets implements Runnable {

    Context context;
    BoughtTicket boughtTicket;
    String address = "sell_ticket";
    int eventID, amount;
    String userId;

    public BuyTickets(Context activity, String userId, int eventID, int amount, String address) {
        this.context = activity;
        boughtTicket = (BoughtTicket)activity;
        this.userId = userId;
        this.eventID = eventID;
        this.amount = amount;
        this.address = address + this.address;


    }

    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection = null;
        Log.e("BUYTICKETSr", "CALLING SERVER NOw!!!!!!");
        try{
            url = new URL( address );
            Log.e("BUYTICKETS","POST " + url.toExternalForm());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parameters = constructMessage();
            if(parameters.equals("")) {
                Log.e("BUYTICKETS","ERROR IN BUYTICKETS!!");
            }
            Log.e("BUYTICKETS" , "MESSAGE SENT TO SERVER");
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.writeBytes(parameters);
            outputStream.flush();
            outputStream.close();

            Log.e("BUYTICKETS" , "MESSAGE RECEIVED FROM SERVER");
            // get response
            int responseCode = urlConnection.getResponseCode();
            Log.e("BUYTICKETS" , "Code received : " + responseCode);
            if(responseCode == 200) {
                String response = ServerOps.readStream(urlConnection.getInputStream());
                Log.e("BUYTICKETS ANS" , response);

                boughtTicket.done(getTicketsFromResponse(response), getVouchersFromResponse(response), getPriceFromResponse(response), getAmountFromResponse(response));
            }


        }catch (Exception e) {
            e.printStackTrace();
            boughtTicket.done(null, null, 0, 0);
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }



    }

    private int getAmountFromResponse(String response) throws JSONException {
        int amount = 0;
        JSONObject jsonObject = new JSONObject(response);
        amount = jsonObject.getInt("numberOfTickets");


        return amount;
    }

    private int getPriceFromResponse(String response) throws JSONException {
        int price = 0;
        JSONObject jsonObject = new JSONObject(response);
        price = jsonObject.getInt("price");


        return price;
    }

    private ArrayList<Voucher> getVouchersFromResponse(String response) throws JSONException {
        ArrayList<Voucher> vouchers = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("vouchers");
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject voucher = jsonArray.getJSONObject(i);
            vouchers.add(new Voucher(voucher.getString("ID"), voucher.getInt("TYPE")));
        }

        return vouchers;
    }

    private ArrayList<Ticket> getTicketsFromResponse(String response) throws JSONException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("tickets");
        String date = jsonObject.getString("date");
        String title = jsonObject.getString("title");

        for(int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject ticket = jsonArray.getJSONObject(i);
            tickets.add(new Ticket(ticket.getString("ID"), ticket.getInt("Place"), title, date));
        }

        return tickets;
    }


    private String constructMessage() {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("userID", userId);
        parameters.put("eventID", ""+eventID);
        parameters.put("amount", ""+amount);

        String s = null;
        try {
            s = ServerOps.getDataString(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        return s;
    }

    public interface BoughtTicket {
        public void done(ArrayList<Ticket> tickets, ArrayList<Voucher> vouchers, int totalPrice, int amount);
    }



}
