package cmov.miguellucas.com.customerapp.ServerOperations;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import cmov.miguellucas.com.customerapp.CreditCard.CreditCardActivity;
import cmov.miguellucas.com.customerapp.Utils.ServerOps;

public class AddCreditCard implements  Runnable {

    String number;
    String date;
    int ccv;
    Context context;
    String address = "add_credit_card";
    CreditCardOP creditCardOP;
    String userID;
    public AddCreditCard (Context context, String number, String date, int ccv, String address, String userID) {
        this.number = number;
        this.date = date;
        this.ccv= ccv;
        this.address = address + this.address;
        this.userID = userID;

        Activity activity = (Activity)context; // parent activity


        try{    // check whether parent has implemented interface or not

            creditCardOP = (CreditCardOP)activity; // interface implemented by parent activity to callback ; now this fragment has a pointer to a function in the activity parent that can be called here and executed over there


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

            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200) {

                creditCardOP.done();
            }
            Log.e("responseCode" ,""+ responseCode);

        }catch (Exception e) {
            e.printStackTrace();
            creditCardOP.done();
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private String constructMessage() {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("type", "" +ccv);
        parameters.put("number", number);
        parameters.put("validity", date);
        parameters.put("UUID", userID);

        Log.e("type", ""+ccv);
        Log.e("number", ""+number);
        Log.e("date", ""+date);
        Log.e("userID", ""+userID);

        String s = null;
        try {
            s = ServerOps.getDataString(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        return s;
    }

    public interface CreditCardOP {
        public void done();
    }

}
