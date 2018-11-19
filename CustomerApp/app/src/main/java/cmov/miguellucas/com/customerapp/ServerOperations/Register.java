package cmov.miguellucas.com.customerapp.ServerOperations;

import android.app.Activity;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cmov.miguellucas.com.customerapp.Utils.ServerOps;

public class Register implements Runnable {
    String name;
    String username;
    String password;
    int NIF;
    String publicKey;
    String address;
    public final String path = "register";
    public RegisterOP registerOP;


    public Register (Context context, String name, String username, String password,
                     int NIF, String publicKey, String address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.NIF = NIF;
        this.publicKey = publicKey;
        this.address = address+path;


        Activity activity = (Activity)context; // parent activity


        try{    // check whether parent has implemented interface or not

            registerOP = (RegisterOP)activity; // interface implemented by parent activity to callback ; now this fragment has a pointer to a function in the activity parent that can be called here and executed over there


        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public void run() {
        URL url;
        HttpURLConnection urlConnection = null;
        Log.e("Register", "CALLING SERVER NOw!!!!!!");
        try{
            url = new URL( address );
            Log.e("REGISTER","POST " + url.toExternalForm());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parameters = constructMessage();
            if(parameters.equals("")) {
                Log.e("REGISTER","ERROR IN REGISTER!!");
            }
            Log.e("REGISTER" , "MESSAGE SENT TO SERVER");
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
                registerOP.done(response);
            }


        }catch (Exception e) {
            e.printStackTrace();
            registerOP.done("");
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

    public interface RegisterOP {
        public void done(String UUID);
    }


}
