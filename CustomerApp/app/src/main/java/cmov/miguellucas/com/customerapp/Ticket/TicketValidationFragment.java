package cmov.miguellucas.com.customerapp.Ticket;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cmov.miguellucas.com.customerapp.Models.Ticket;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Utils.QRCodeGenerator;
import cmov.miguellucas.com.customerapp.Utils.SharedPreferenceConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketValidationFragment extends Fragment implements QRCodeGenerator.QRAnswer {

    ImageView qrCode;
    TextView place = null;
    Button button;
    BackToTicketList backToTicketList;
    Ticket ticket;
    ProgressBar progressBar;


    public TicketValidationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_validation, container, false);
        ticket = (Ticket) getArguments().getSerializable("ticket");

        qrCode = view.findViewById(R.id.qr_code);
        place = view.findViewById(R.id.place);
        button = view.findViewById(R.id.btn_back);
        progressBar = view.findViewById(R.id.progressBar);
        if(place == null) {
            Log.e("TICKET VALIDATION" , "Place is null ");
        }

        Log.e("TICKET VALIDATION" , "Ticket place: " + ticket.Place);
        place.setText("" + ticket.Place);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToTicketList.done();
            }
        });

        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator(prepareData(), getContext(),this);
        Thread thread = new Thread(qrCodeGenerator);
        thread.start();

        return view;
    }

    private String prepareData() {
        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(getContext());
        String userID = sharedPreferenceConfig.readUUID();
        return "ticketID=" + ticket.ID + "&userID=" + userID;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.backToTicketList = (BackToTicketList) getActivity();
    }

    @Override
    public void done(final Bitmap bitmap) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                qrCode.setImageBitmap(bitmap);
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    public interface BackToTicketList {
        public void done();
    }


}
