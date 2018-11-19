package cmov.miguellucas.com.customerapp.Home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.Models.Event;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.ServerOperations.GetEvents;
import cmov.miguellucas.com.customerapp.Utils.PostsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements GetEvents.ListEventsOP {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String address = "http://10.0.2.2:7074/";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);


        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new PostsAdapter(new ArrayList<Event>(), getActivity());
        recyclerView.setAdapter(mAdapter);

        GetEvents getEvents = new GetEvents(address, this);
        Thread thread = new Thread(getEvents);
        thread.start();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        address = getResources().getString(R.string.address);
    }

    @Override
    public void done(final ArrayList<Event> events) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            try{
                mAdapter = new PostsAdapter(events, getActivity());
                recyclerView.setAdapter(mAdapter);
                Log.e("HOME FRAG" , "number of events: " + events.size());
            }catch (Exception e) {
                e.printStackTrace();
            }

            }

        });


    }



}
