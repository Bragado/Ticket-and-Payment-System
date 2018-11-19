package cmov.miguellucas.com.customerapp.Transactions;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.Database.TicketDbHelper;
import cmov.miguellucas.com.customerapp.Models.Ticket;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Utils.TicketsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ChangeTab changeTab;
    TextView textView1, textView2;


    public TicketsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(view.getContext());

        ArrayList<Ticket> tickets = getTicketsInLocalDB();

        mAdapter = new TicketsAdapter(getActivity(), tickets, false);
        recyclerView.setAdapter(mAdapter);

        changeTab = (ChangeTab)getActivity();
        textView1 = view.findViewById(R.id.textView3);
        textView2 = view.findViewById(R.id.textView4);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab.change(1);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab.change(2);
            }
        });

        return view;
    }

    private ArrayList<Ticket> getTicketsInLocalDB() {
        TicketDbHelper contactDbHelper = new TicketDbHelper(getContext());
        SQLiteDatabase db = contactDbHelper.getReadableDatabase();

        return contactDbHelper.readTickets(db, 1);
    }

    public interface ChangeTab {
        public void change(int i);
    }

}
