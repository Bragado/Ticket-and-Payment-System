package cmov.miguellucas.com.customerapp.Transactions;


import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.Database.TicketDbHelper;
import cmov.miguellucas.com.customerapp.Models.Product;
import cmov.miguellucas.com.customerapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderProductsFragment extends Fragment {

    TextView Coffe, Popcorn, Sandwish, Soda;
    Context context;
    Activity main;
    int ID;
    public OrderProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_products, container, false);
        Coffe = view.findViewById(R.id.order_coffee_number);
        Popcorn = view.findViewById(R.id.order_popcorn_number);
        Sandwish = view.findViewById(R.id.order_sandwich_number);
        Soda = view.findViewById(R.id.order_sandwich_number);
        this.context = getContext();
        this.main = getActivity();
        ID = getArguments().getInt("ID");

        new Thread(new Runnable() {
            @Override
            public void run() {
                TicketDbHelper contactDbHelper = new TicketDbHelper(getContext());
                SQLiteDatabase db = contactDbHelper.getReadableDatabase();
                final ArrayList<Product> products = contactDbHelper.readProducts(db, ID);

                main.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < products.size(); i++) {
                            switch (products.get(i).type) {
                                case 1:
                                    Coffe.setText("x " + products.get(i).quantity);
                                    break;
                                case 2:
                                    Soda.setText("x " + products.get(i).quantity);
                                    break;
                                case 3:
                                    Popcorn.setText("x " + products.get(i).quantity);
                                    break;
                                case 4:
                                    Sandwish.setText("x " + products.get(i).quantity);
                                    break;
                            }
                        }
                    }
                });



            }
        }).start();


        return view;
    }




}
