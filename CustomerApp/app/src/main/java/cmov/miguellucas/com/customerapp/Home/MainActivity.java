package cmov.miguellucas.com.customerapp.Home;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.CreditCard.CreditCardActivity;
import cmov.miguellucas.com.customerapp.Database.TicketDbHelper;
import cmov.miguellucas.com.customerapp.Models.Event;
import cmov.miguellucas.com.customerapp.Models.Ticket;
import cmov.miguellucas.com.customerapp.Models.Voucher;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Register.RegisterActivity;
import cmov.miguellucas.com.customerapp.ServerOperations.BuyTickets;
import cmov.miguellucas.com.customerapp.Utils.BottomNavigationViewHelper;
import cmov.miguellucas.com.customerapp.Utils.PostsAdapter;
import cmov.miguellucas.com.customerapp.Utils.SharedPreferenceConfig;

public class MainActivity extends AppCompatActivity implements PostsAdapter.SeeTicket, BuyTickets.BoughtTicket, ConfirmationFragment.Done {

    private static final int ACTIVITY_NUM = 0;
    private SharedPreferenceConfig sharedPreferenceConfig;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigationView();

        sharedPreferenceConfig =  new SharedPreferenceConfig(getApplicationContext());

        //sharedPreferenceConfig.writeLoginStatus(false, "");
        if(!sharedPreferenceConfig.readLogin()) {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
            return;
        } else if(findViewById(R.id.relLayout2) != null) {
            if(savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.relLayout2, new HomeFragment(), null).commit();
        }

    }

    private void setupBottomNavigationView() {

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    @Override
    public void openTicket(Event e) {
        Log.e("Main Activity", "User Clicked to see ticket with id = " + e.ID);

        sharedPreferenceConfig =  new SharedPreferenceConfig(getApplicationContext());
        if(!sharedPreferenceConfig.readCreditCard()) {
            startActivity(new Intent(this, CreditCardActivity.class));
            finish();
            return;
        }


        EventFragment eventFragment = new EventFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("event", e);
        eventFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.relLayout2, eventFragment, null).addToBackStack(null).commit();


    }

    @Override
    public void done(final ArrayList<Ticket> tickets,final ArrayList<Voucher> vouchers, final int totalPrice, final int amount) {
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                TicketDbHelper contactDbHelper = new TicketDbHelper(context);
                SQLiteDatabase db = contactDbHelper.getWritableDatabase();
                Ticket ticket;
                //contactDbHelper.onCreate(db);

                for (int i = 0; i < tickets.size(); i++) {
                    ticket = tickets.get(i);
                    contactDbHelper.AddTicket(db, ticket.ID, ticket.Place, ticket.eventTitle, ticket.date, 0);
                }

                Voucher voucher;
                for(int i = 0; i < vouchers.size(); i++) {
                    voucher = vouchers.get(i);
                    contactDbHelper.AddVoucher(db, voucher.description, voucher.ID, voucher.type);
                }

            }
        });
        thread.start();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                ConfirmationFragment confirmationFragment = new ConfirmationFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("price", totalPrice);
                bundle.putInt("amount", amount);
                confirmationFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.relLayout2, confirmationFragment, null).commit();

            }
        });



    }

    @Override
    public void confirmationDone() {
        getSupportFragmentManager().beginTransaction().replace(R.id.relLayout2, new HomeFragment(), null).commit();
    }
}
