package ServerRequests;


import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import logic.Cafeteria;
import logic.Customer;
import logic.Order;

public class CreateOrder implements Runnable {

    private String path = "/validate_voucher";
    private Order order;

    public CreateOrder (Order order) {
        this.order = order;
    }


    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection = null;
        try{
            url = new URL(ServerUtils.server_path + path);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(order.toJson().toString());
            Log.d("createorder", order.toJson().toString());
            writer.flush();
            writer.close();
            os.close();

            // get response
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200) {
                String response = ServerUtils.readStream(urlConnection.getInputStream());
                JSONObject responseObj = ServerUtils.parseOrder(response);
                double price = -1;
                price = responseObj.getDouble("price");
                JSONArray responseArr = responseObj.getJSONArray("vouchers");
                ArrayList<String> vouchers = new ArrayList<>();
                for (int i = 0;i<responseArr.length();i++){
                    vouchers.add(responseArr.getString(i));
                }

                if (price != -1){
                    DecimalFormat df = new DecimalFormat("#.##");
                    price = Double.valueOf(df.format(price));
                    order.setPrice(price);
                    order.checkVouchers(vouchers);
                    Cafeteria.getInstance().addOrder(order);
                    Log.d("createorder", "Success");
                    Log.d("createorder", "response code: " + response);
                    Log.d("createorder", "Price: " + price);
                } else {
                    Log.d("createorder" , "ERROR " + response);
                }

            } else {
                Log.d("createorder" , "response code:" + responseCode);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

}
