package cmov.miguellucas.com.customerapp.ServerOperations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import cmov.miguellucas.com.customerapp.Database.TicketDbHelper;
import cmov.miguellucas.com.customerapp.Models.Ticket;
import cmov.miguellucas.com.customerapp.Models.Voucher;
import cmov.miguellucas.com.customerapp.Transactions.UpdateDatabaseFragment;
import cmov.miguellucas.com.customerapp.Utils.ServerOps;

public class GetTransactions implements Runnable {

    Context context;
    String userID;
    String address = "getTrasanctions";
    Done done;

    public GetTransactions(Context context, String userID, String address, UpdateDatabaseFragment upd) {
        this.context = context;
        this.userID = userID;
        this.address = address + this.address;
        done = (Done)upd;
    }


    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection = null;
        Log.e("Register", "CALLING SERVER NOw!!!!!!");
        try{
            url = new URL( address);
            Log.e("REGISTER","POST " + url.toExternalForm());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parameters = constructMessage();
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.writeBytes(parameters);
            outputStream.flush();
            outputStream.close();
            Log.e("REGISTER" , "MESSAGE RECEIVED FROM SERVER");
            // get response
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200) {
                String response = ServerOps.readStream(urlConnection.getInputStream());
                Log.e("REGISTER ANS" , response);

                insertIntoDataBase(response);
            }
            Log.e("UPDATE ANS" , "NAO RECEBEMOS NADA DE JEITO, Codigo: " + responseCode);

        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private String constructMessage() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("userID", userID);

        String s = null;
        try {
            s = ServerOps.getDataString(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        return s;
    }

    public void insertIntoDataBase(String resp) throws JSONException {
        JSONObject object = new JSONObject(resp);
        TicketDbHelper ticketDbHelper = new TicketDbHelper(context);
        SQLiteDatabase db = ticketDbHelper.getWritableDatabase();


        /* Update Vouchers Deleted but never used*/

        JSONArray vouchersNotused = object.getJSONArray("vouchersNotUsed");
        for(int i = 0; i < vouchersNotused.length(); i++) {
            JSONObject ticket = vouchersNotused.getJSONObject(i);
            Voucher t = new Voucher(ticket.getString("ID"), ticket.getInt("TYPE"));
            ticketDbHelper.AddVoucher(db, t.description, t.ID, t.type);
        }

        /* Update tickets to used */

        JSONArray ticketsUsed = object.getJSONArray("ticketsUsed");
        for(int i = 0; i < ticketsUsed.length(); i++) {
            String ticketID = ticketsUsed.getString(i);
            ticketDbHelper.setTicketUsed(db, ticketID);
        }


        /* Insert Orders */
        JSONArray orders = object.getJSONArray("cafeteriaOrder");
        for(int i = 0; i < orders.length(); i++) {
            JSONObject obj = orders.getJSONObject(i);
            String date = obj.getString("DATE");
            String price = obj.getString("PRICE");
            int ID = obj.getInt("ID");

            try {
                ticketDbHelper.addOrder(db, ID, date, price);
            }catch (Exception e) {
                continue;
            }

            JSONArray products = obj.getJSONArray("PRODUCTS");
            for(int j = 0; j < products.length(); j++) {
                JSONObject object1 = products.getJSONObject(j); //{"QUANTITY":3,"IDPRODUCT":1}
                int quantity = object1.getInt("QUANTITY");
                int productType = object1.getInt("IDPRODUCT");
                ticketDbHelper.addProductOrder(db, ID, quantity, productType);
            }

        }
        try{
            done.done();
        }catch (Exception e) {

        }


    }

    public interface Done {
        public void done();
    }



}
