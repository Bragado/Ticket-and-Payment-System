package cmov.miguellucas.com.cafeteriaterminal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

import logic.Cafeteria;
import logic.Order;
import logic.Product;

public class OrderActivity extends AppCompatActivity {

    private Order order;
    private ToggleButton btnOrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle("Order Details");

        order = (Order) getIntent().getSerializableExtra(OrderListActivity.ID_EXTRA);

        buildLayout();

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

    @Override
    public void finish() {
        Intent i = new Intent();
        i.putExtra(OrderListActivity.ID_EXTRA, order);
        setResult(RESULT_OK, i);

        super.finish();
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

    private void buildLayout(){
        //set order id
        TextView idText = findViewById(R.id.order_id);
        idText.setText("Order #" + order.getId());

        //add one row per product
        LinearLayout linear = (LinearLayout)findViewById(R.id.order_items_list);
        LayoutInflater inflater = LayoutInflater.from(this);
        for(Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {

            //fetch the row to add to the list
            //LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.order_row, null, false);

            //set product name
            TextView orderText = row.findViewById(R.id.order);
            orderText.setText(entry.getKey().getName());

            //set price detail
            TextView priceTextDetail = row.findViewById(R.id.price_detail);
            priceTextDetail.setText(entry.getValue() + " x " + entry.getKey().getPrice() + " €");

            //set price total
            TextView priceText = row.findViewById(R.id.price_total);
            priceText.setText((entry.getValue() * entry.getKey().getPrice()) + " €");

            //add the row to the list
            linear.addView(row);
        }

        //add 1st voucher
        if (order.getVoucher1() != null){

            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.order_row, null, false);
            TextView voucherText = row.findViewById(R.id.order);
            voucherText.setText("Voucher #" + order.getVoucher1().getId());
            TextView voucherDiscountText = row.findViewById(R.id.price_total);
            voucherDiscountText.setText("- " + order.getVoucher1().getDiscount() + " €");
            linear.addView(row);
        }

        //add 2nd voucher
        if (order.getVoucher2() != null){
            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.order_row, null, false);
            TextView voucherText = row.findViewById(R.id.order);
            voucherText.setText("Voucher #" + order.getVoucher2().getId());
            TextView voucherDiscountText = row.findViewById(R.id.price_total);
            voucherDiscountText.setText("- " + order.getVoucher2().getDiscount() + " €");
            linear.addView(row);
        }

        //add total price
        TextView totalText = findViewById(R.id.order_total);
        totalText.setText(order.getPrice() + " €");

        //set client info - TODO
        TextView clientNameText = this.findViewById(R.id.order_client_name);
        clientNameText.setText("Miguel Lucas");

        //set client nif - TODO
        TextView clientNifText = this.findViewById(R.id.order_client_nif);
        clientNifText.setText("123456789");
    }

    public void goBackClick(View v){
        finish();
    }


}
