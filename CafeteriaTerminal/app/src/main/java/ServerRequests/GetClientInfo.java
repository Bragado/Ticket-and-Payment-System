package ServerRequests;

import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import logic.Customer;

public class GetClientInfo implements Runnable{
    private String user_id;
    private final String path = "/clientInfo";

    private Customer customer;

    public GetClientInfo (String user_id) {
        this.user_id = user_id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection = null;
        try{
            url = new URL(ServerUtils.server_path + path + "?userID=" + user_id);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // get response
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200) {
                String response = ServerUtils.readStream(urlConnection.getInputStream());
                Customer customer = ServerUtils.parseCustomer(response);
                if (customer != null){
                    this.customer = customer;
                    Log.d("clientinfo" , response);
                    Log.d("clientinfo", customer.getName());
                } else {
                    Log.d("clientinfo" , "ERROR " + response);
                }

            } else {
                Log.d("clientinfo" , "" + responseCode);
            }
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
        parameters.put("user_id", user_id);

        String s = null;
        try {
            s = ServerUtils.getDataString(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        return s;
    }
}
