package cmov.miguellucas.com.customerapp.Transactions;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.ServerOperations.GetTransactions;
import cmov.miguellucas.com.customerapp.Utils.SharedPreferenceConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateDatabaseFragment extends Fragment implements GetTransactions.Done {

    ProgressBar progressBar;
    ChangeTab changeTab;
    TextView textView1, textView2;
    Button update;
    public String address = "http://10.0.2.2:7074/";
    UpdateDatabaseFragment THIS;

    public UpdateDatabaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_database, container, false);
        THIS = this;
        changeTab = (ChangeTab)getActivity();
        textView1 = view.findViewById(R.id.textView2);
        textView2 = view.findViewById(R.id.textView3);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab.change(0);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab.change(1);
            }
        });
        update = view.findViewById(R.id.btn_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(getContext());
                String userID = sharedPreferenceConfig.readUUID();
                progressBar.setVisibility(View.VISIBLE);
                Thread thread = new Thread(new GetTransactions(getContext(), userID, address, THIS));
                thread.start();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getResources().getString(R.string.address);
    }

    @Override
    public void done() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });

    }


    public interface ChangeTab {
        public void change(int i);
    }


}
