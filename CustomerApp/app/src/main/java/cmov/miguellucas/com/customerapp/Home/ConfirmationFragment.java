package cmov.miguellucas.com.customerapp.Home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cmov.miguellucas.com.customerapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {


    public Done done;
    int price, amount;

    TextView message;
    Button btn_back;


    public ConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        price = getArguments().getInt("price");
        amount = getArguments().getInt("amount");

        message = view.findViewById(R.id.message);
        btn_back = view.findViewById(R.id.btn_back);

        if(price != 0)
            message.setText("Congratulations, you just bought " + amount + " tickets for the price of " + price + "€");
        else
            message.setText("Oops, something went wrong, pleas try again later");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done.confirmationDone();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        done = (Done)getActivity();
    }

    public interface Done {
        public void confirmationDone();
    }

}
