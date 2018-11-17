package cmov.miguellucas.com.customerapp.Home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigationView();

       /* if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HomeFragment(), null).commit();
        }*/

    }

    private void setupBottomNavigationView() {

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }



}
