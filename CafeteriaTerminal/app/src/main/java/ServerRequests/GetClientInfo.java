package ServerRequests;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class GetClientInfo implements Runnable{
    String user_id
    public final String path = "/register";

    public Register (String name, String username, String password,
                     int NIF, String publicKey, String address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.NIF = NIF;
        this.publicKey = publicKey;
        this.address = address+path;
    }



    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection = null;
        try{
            url = new URL("http://" + address + ":8701/Rest/users");
            Log.d("REGISTER","POST " + url.toExternalForm());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parameters = constructMessage();
            if(parameters.equals("")) {
                Log.d("REGISTER","ERROR IN REGISTER!!");
            }
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.writeBytes(parameters);
            outputStream.flush();
            outputStream.close();

            // get response
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200) {
                String response = ServerOps.readStream(urlConnection.getInputStream());
                Log.d("REGISTER ANS" , response);

            }


        }catch (Exception e) {

        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private String constructMessage() {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("username", username);
        parameters.put("password", password);
        parameters.put("name", name);
        parameters.put("nif", NIF + "");
        parameters.put("publicKey", publicKey);

        String s = null;
        try {
            s = ServerOps.getDataString(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        return s;
    }
}
