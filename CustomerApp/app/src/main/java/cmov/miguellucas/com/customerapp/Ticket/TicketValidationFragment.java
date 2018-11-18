package cmov.miguellucas.com.customerapp.Ticket;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cmov.miguellucas.com.customerapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketValidationFragment extends Fragment {


    public TicketValidationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_validation, container, false);
        return view;
    }

}
