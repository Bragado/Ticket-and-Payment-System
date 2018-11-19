package cmov.miguellucas.com.customerapp.Ticket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import cmov.miguellucas.com.customerapp.Models.Ticket;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Utils.BottomNavigationViewHelper;
import cmov.miguellucas.com.customerapp.Utils.TicketsAdapter;

public class TicketActivity extends AppCompatActivity implements TicketsAdapter.GenerateQRCodeForTicket, TicketValidationFragment.BackToTicketList {

    private static final int ACTIVITY_NUM = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigationView();

        if(findViewById(R.id.relLayout2) != null) {
            if(savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.relLayout2, new TicketListFragment(), null).commit();
        }

    }

    private void setupBottomNavigationView() {

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);    }

    @Override
    public void generateQR(Ticket ticket) {
        Log.e("TICKET ACTIVITY", "User wants to generate qr code");
        TicketValidationFragment ticketValidationFragment = new TicketValidationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticket", ticket);
        ticketValidationFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.relLayout2, ticketValidationFragment, null).addToBackStack(null).commit();

    }

    @Override
    public void done() {
        getSupportFragmentManager().beginTransaction().replace(R.id.relLayout2, new TicketListFragment(), null).commit();
    }
}
