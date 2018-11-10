package cmov.miguellucas.com.cafeteriaterminal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import logic.Cafeteria;
import logic.Order;

public class OrderActivity extends AppCompatActivity {

    private Order order;
    private ToggleButton btnOrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        order = (Order) getIntent().getSerializableExtra(OrderListActivity.ID_EXTRA);

        btnOrderStatus = findViewById(R.id.btn_order_status);

        if (order.getOrderServed()) {
            changeOrderStatus(true);

        } else {
            changeOrderStatus(false);
        }


        btnOrderStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeOrderStatus(isChecked);
            }
        });
    }

    public void changeOrderStatus(boolean newStatus) {
        btnOrderStatus.setChecked(newStatus);
        ImageView icon = findViewById(R.id.order_status_icon);
        TextView statusText = findViewById(R.id.order_status);

        if (newStatus){
            icon.setImageResource(R.drawable.circle_green);
            statusText.setText(R.string.order_served);
        } else {
            icon.setImageResource(R.drawable.circle_red);
            statusText.setText(R.string.order_not_served);

        }

        order.setOrderServed(newStatus);
    }


}
