package cmov.miguellucas.com.customerapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import cmov.miguellucas.com.customerapp.Cafeteria.CafeteriaActivity;
import cmov.miguellucas.com.customerapp.Home.MainActivity;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Ticket.TicketActivity;
import cmov.miguellucas.com.customerapp.Transactions.TransactionsActivity;

public class BottomNavigationViewHelper {

    private static final String TAG  = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG, "setupBottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);

    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.ic_house:
                        Intent intent1 = new Intent(context, MainActivity.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_cafeteria:
                        Intent intent2 = new Intent(context, CafeteriaActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_ticket:
                        Intent intent3 = new Intent(context, TicketActivity.class);
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_transactions:
                        Intent intent4 = new Intent(context, TransactionsActivity.class);
                        context.startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }

}
