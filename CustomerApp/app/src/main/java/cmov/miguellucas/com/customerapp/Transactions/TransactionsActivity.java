package cmov.miguellucas.com.customerapp.Transactions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import cmov.miguellucas.com.customerapp.Home.HomeFragment;
import cmov.miguellucas.com.customerapp.Models.Order;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Utils.BottomNavigationViewHelper;
import cmov.miguellucas.com.customerapp.Utils.OrdersAdapter;

public class TransactionsActivity extends AppCompatActivity  implements OrdersFragment.ChangeTab, TicketsFragment.ChangeTab, UpdateDatabaseFragment.ChangeTab, OrdersAdapter.OpenOrder  {

    private static final int ACTIVITY_NUM = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigationView();

        if(findViewById(R.id.relLayout2) != null) {
            if(savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.relLayout2, new TicketsFragment(), null).commit();
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
    public void change(int i) {
        switch (i) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.relLayout2, new TicketsFragment(), null).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.relLayout2, new OrdersFragment(), null).commit();

                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.relLayout2, new UpdateDatabaseFragment(), null).addToBackStack(null).commit();

                break;
        }
    }

    @Override
    public void open(Order order) {
        OrderProductsFragment orderProductsFragment = new OrderProductsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", order.ID);
        bundle.putDouble("PRICE", order.price);
        bundle.putString("DATE", order.date);
        orderProductsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.relLayout2, orderProductsFragment, null).addToBackStack(null).commit();
    }
}
