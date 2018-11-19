package cmov.miguellucas.com.customerapp.ServerOperations;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.Home.HomeFragment;
import cmov.miguellucas.com.customerapp.Models.Event;
import cmov.miguellucas.com.customerapp.Utils.ServerOps;

public class GetEvents implements Runnable {

    public final String path = "listEvents";
    public GetEvents.ListEventsOP listEventsOP;
    public String address;
    public String baseAddress;

    public GetEvents(String address,   HomeFragment homeFragment) {

        this.baseAddress = address;
        this.address = address + path;


        try {    // check whether parent has implemented interface or not

            listEventsOP = (GetEvents.ListEventsOP) homeFragment; // interface implemented by parent activity to callback ; now this fragment has a pointer to a function in the activity parent that can be called here and executed over there


        } catch (Exception e) {
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


            Log.e("REGISTER" , "MESSAGE RECEIVED FROM SERVER");
            // get response
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200) {
                String response = ServerOps.readStream(urlConnection.getInputStream());
                Log.e("REGISTER ANS" , response);

                listEventsOP.done(parseAnswer(response));
            }


        }catch (Exception e) {
            e.printStackTrace();
            listEventsOP.done(null);
        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }


    public ArrayList<Event> parseAnswer(String Answer) throws JSONException {
        ArrayList<Event> events = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(Answer);
        JSONArray jsonArray = jsonObject.getJSONArray("List");
        String title, description, date, photo;
        int capacity, ticketPrice, ID;
        JSONObject event;

        for(int i = 0; i < jsonArray.length(); i++) {
            event = jsonArray.getJSONObject(i);
            title = event.getString("Title");
            description = event.getString("Description");
            date = event.getString("Date");
            photo = event.getString("photoName");
            capacity = event.getInt("Capacity");
            ID = event.getInt("ID");
            ticketPrice = event.getInt("TicketPrice");
            photo = baseAddress + "getEventImage?photoName=" + photo;
            events.add(new Event(ID, capacity, ticketPrice,photo, title, description, date));

        }

        return events;
    }


    public interface ListEventsOP {
        public void done(ArrayList<Event> events);
    }



}
