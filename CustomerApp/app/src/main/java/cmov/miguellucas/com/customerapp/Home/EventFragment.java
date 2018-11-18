package cmov.miguellucas.com.customerapp.Home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import cmov.miguellucas.com.customerapp.Models.Event;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.ServerOperations.BuyTickets;
import cmov.miguellucas.com.customerapp.Utils.SharedPreferenceConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    Event event = null;
    TextView description, title, date, capacity, ticket_price;
    EditText nTickets;
    ImageView photo;
    Button btn;
    public String address = "http://10.0.2.2:7074/";
    ProgressBar progressBar;


    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        event = (Event) getArguments().getSerializable("event");

        description = view.findViewById(R.id.description);
        title = view.findViewById(R.id.title);
        date = view.findViewById(R.id.date);
        capacity = view.findViewById(R.id.capacity);
        ticket_price = view.findViewById(R.id.ticket_price);
        description = view.findViewById(R.id.description);
        photo = view.findViewById(R.id.photo);
        nTickets = view.findViewById(R.id.nTickets);
        btn = view.findViewById(R.id.btn_bur);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(getContext());
                String uuid = sharedPreferenceConfig.readUUID();
                String ntickets = nTickets.getText().toString();
                int amount;
                try {
                    amount = Integer.parseInt(ntickets);
                }catch (Exception e) {
                    Toast.makeText(getContext(), "Please introduce a valid value", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                BuyTickets buyTickets = new BuyTickets(getActivity(), uuid, event.ID, amount, address);
                Thread thread = new Thread(buyTickets);
                thread.start();
            }
        });

        initPage();



        return view;
    }

    private void initPage() {
        Picasso.get().load(event.photoName).into(photo);
        description.setText(event.Description);
        capacity.setText("Capacity Left: " + event.capacity);
        date.setText("Date: " + event.date);
        title.setText(event.title);
        ticket_price.setText("Ticket Price: " + event.ticketPrice + "€");

    }


}
