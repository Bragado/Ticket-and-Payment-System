package cmov.miguellucas.com.customerapp.Cafeteria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Utils.BottomNavigationViewHelper;

public class CafeteriaActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 1;

    private final int VOUCHER_NONE = 0;
    private final int VOUCHER_COFFEE = 1;
    private final int VOUCHER_POPCORN = 2;
    private final int VOUCHER_DISCOUNT = 3;

    private int voucher1 = VOUCHER_NONE;
    private int voucher2 = VOUCHER_NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cafeteria);

        TextView coffeeNumberText = findViewById(R.id.voucher_coffee_number);
        coffeeNumberText.setText(String.format(getString(R.string.voucher_number), 3));
        TextView popcornNumberText = findViewById(R.id.voucher_popcorn_number);
        popcornNumberText.setText(String.format(getString(R.string.voucher_number), 1));
        TextView discountNumberText = findViewById(R.id.voucher_discount_number);
        discountNumberText.setText(String.format(getString(R.string.voucher_number), 1));

        buildLayout();
    }

    public void buildLayout(){

        LinearLayout coffeeLayout = findViewById(R.id.layout_coffee);
        LinearLayout popcornLayout = findViewById(R.id.layout_popcorn);
        LinearLayout discountLayout = findViewById(R.id.layout_discount);
        //set click listeners to voucher layouts
        coffeeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVoucher(VOUCHER_COFFEE);
            }
        });

        popcornLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVoucher(VOUCHER_POPCORN);
            }
        });

        discountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVoucher(VOUCHER_DISCOUNT);
            }
        });
    }

    public void selectVoucher(int voucher){
        //in case voucher is already selected -> deselect it
        if (voucher1 == voucher){
            voucher1 = voucher2;
            voucher2 = VOUCHER_NONE;
        } else if (voucher2 == voucher){
            voucher2 = VOUCHER_NONE;
        } else {
            //if its not selected -> select it
            if (voucher1 == VOUCHER_NONE){
                voucher1 = voucher;
            } else if (voucher2 == VOUCHER_NONE){
                voucher2 = voucher;
            } else {
                voucher1 = voucher2;
                voucher2 = voucher;
            }
        }

        toggleSelectedImgs();
    }

    public void toggleSelectedImgs(){
        ImageView coffeeImg = findViewById(R.id.img_coffee_selected);
        ImageView popcornImg = findViewById(R.id.img_popcorn_selected);
        ImageView discountImg = findViewById(R.id.img_discount_selected);
        View coffeeActive = findViewById(R.id.voucher_coffee_active);
        View popcornActive = findViewById(R.id.voucher_popcorn_active);
        View discountActive = findViewById(R.id.voucher_discount_active);

        if (voucher1 == VOUCHER_COFFEE || voucher2 == VOUCHER_COFFEE){
            coffeeImg.setVisibility(View.VISIBLE);
            coffeeActive.setVisibility(View.VISIBLE);
        } else {
            coffeeImg.setVisibility(View.INVISIBLE);
            coffeeActive.setVisibility(View.INVISIBLE);
        }

        if (voucher1 == VOUCHER_POPCORN || voucher2 == VOUCHER_POPCORN){
            popcornImg.setVisibility(View.VISIBLE);
            popcornActive.setVisibility(View.VISIBLE);
        } else {
            popcornImg.setVisibility(View.INVISIBLE);
            popcornActive.setVisibility(View.INVISIBLE);
        }

        if (voucher1 == VOUCHER_DISCOUNT || voucher2 == VOUCHER_DISCOUNT){
            discountImg.setVisibility(View.VISIBLE);
            discountActive.setVisibility(View.VISIBLE);
        } else {
            discountImg.setVisibility(View.INVISIBLE);
            discountActive.setVisibility(View.INVISIBLE);
        }
    }

    public void generateOrder(View v){

    }
}
