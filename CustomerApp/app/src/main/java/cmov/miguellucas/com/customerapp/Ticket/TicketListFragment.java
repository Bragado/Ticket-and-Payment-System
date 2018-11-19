package cmov.miguellucas.com.customerapp.Ticket;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.Database.TicketDbHelper;
import cmov.miguellucas.com.customerapp.Models.Ticket;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Utils.TicketsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String address = "http://10.0.2.2:7074/";

    public TicketListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(view.getContext());

        ArrayList<Ticket> tickets = getTicketsInLocalDB();

        mAdapter = new TicketsAdapter(getActivity(), tickets, true);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        address = getResources().getString(R.string.address);
    }

    private ArrayList<Ticket> getTicketsInLocalDB() {
        TicketDbHelper contactDbHelper = new TicketDbHelper(getContext());
        SQLiteDatabase db = contactDbHelper.getReadableDatabase();
        //contactDbHelper.onCreate(db);
        return contactDbHelper.readTickets(db, 0);
    }

}
