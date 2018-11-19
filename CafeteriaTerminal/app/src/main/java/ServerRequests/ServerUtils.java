package ServerRequests;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import logic.Customer;
import logic.Order;
import logic.Product;
import logic.Voucher;

public class ServerUtils {
    public static final String server_path = "http://10.0.2.2:7074";

    public static String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException, UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        catch (IOException e) {
            return e.getMessage();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    return e.getMessage();
                }
            }
        }
        return response.toString();
    }

    public static Customer parseCustomer(String customer){
        try {
            JSONObject jsonObject = new JSONObject(customer);
            return new Customer(jsonObject.getString("name"), jsonObject.getString("nif"));
        } catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject parseOrder(String order){
        try {
            JSONObject jsonObject = new JSONObject(order);
            return jsonObject;
        } catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    public static Order parseOrderFromQR(String json){
        try{
            Order order = new Order(1);

            JSONObject jo = new JSONObject(json);
            String userId = jo.getString("userID");
            String signedUserId = jo.getString("signedUserID");
            JSONArray products = jo.getJSONArray("products");
            JSONArray vouchers = jo.getJSONArray("vouchers");

            for (int i=0;i < products.length(); i++){
                JSONObject product = products.getJSONObject(i);
                int type = product.getInt("type");
                int quantity = product.getInt("quantity");
                Product newProduct = new Product(type);
                for (int j=0; j<quantity;j++){
                    order.addProduct(newProduct);
                }
            }

            for (int i=0;i < vouchers.length(); i++){
                String voucher = vouchers.getString(i);
                Voucher newVoucher = new Voucher(voucher,0);
                if (order.getVoucher1() == null){
                    order.setVoucher1(newVoucher);
                } else {
                    order.setVoucher2(newVoucher);
                }
            }

            order.setUserId(userId);
            order.setSignedUserId(signedUserId);

            return order;

        } catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }
}
