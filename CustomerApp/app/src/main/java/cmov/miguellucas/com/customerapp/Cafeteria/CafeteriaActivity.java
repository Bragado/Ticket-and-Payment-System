package cmov.miguellucas.com.customerapp.Cafeteria;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONObject;

import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Utils.BottomNavigationViewHelper;

public class CafeteriaActivity extends AppCompatActivity implements CafeteriaFragment.GenerateQrCode {

    private static final int ACTIVITY_NUM = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigationView();

        if(findViewById(R.id.relLayout2) != null) {
            if(savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.relLayout2, new CafeteriaFragment(), null).commit();
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
    public void generate(JSONObject jsonObject) {
        CafeteriaQrCode cafeteriaFragment = new CafeteriaQrCode();
        Bundle bundle = new Bundle();
        bundle.putString("data", jsonObject.toString());
        cafeteriaFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.relLayout2, cafeteriaFragment, null).addToBackStack(null).commit();

    }
}
