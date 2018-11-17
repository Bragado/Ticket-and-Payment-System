package cmov.miguellucas.com.cafeteriaterminal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ServerRequests.GetClientInfo;
import logic.Cafeteria;
import logic.Customer;
import logic.Order;

public class OrderListActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    public final static String ID_EXTRA_ORDER = "org.proj1.cafeteriaTerminal.order";
    public final static String ID_EXTRA_CUSTOMER = "org.proj1.cafeteriaTerminal.customer";
    private final static int ORDER_REQUEST_CODE = 0;
    private OrderAdapter orderAdapter;
    private String DEBUG_TAG = "CMOV_MIGUEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        setTitle(R.string.title_order_list_activity);

        ListView list = findViewById(R.id.order_list);
        orderAdapter = new OrderAdapter();

        list.setAdapter(orderAdapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        GetClientInfo getClientInfo = new GetClientInfo("7614d302-b79c-4591-9e35-af3a5caf1717");
        Thread thread = new Thread(getClientInfo);
        thread.start();
        try {
            thread.join();

            //get customer from request
            Customer customer = getClientInfo.getCustomer();

            Intent i = new Intent(this, OrderActivity.class);
            i.putExtra(ID_EXTRA_ORDER, Cafeteria.getInstance().getOrders().get(pos));
            i.putExtra(ID_EXTRA_CUSTOMER, customer);
            startActivityForResult(i, ORDER_REQUEST_CODE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ORDER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Order order = (Order) data.getSerializableExtra(ID_EXTRA_ORDER);
                int orderPos = Cafeteria.getInstance().getOrders().indexOf(order);
                Cafeteria.getInstance().getOrders().set(orderPos, order);
            }
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        orderAdapter.refreshOrders();
    }

    class OrderAdapter extends ArrayAdapter<Order> {
        OrderAdapter() {
            super(OrderListActivity.this, R.layout.list_order_row, Cafeteria.getInstance().getOrders());
        }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.list_order_row, parent, false);    // get our custom layout
            }
            Order order = Cafeteria.getInstance().getOrders().get(position);

            if (order.getOrderServed()) {
                row.findViewById(R.id.list_order_row).setBackgroundResource(R.drawable.border_right_green);
            } else {
                row.findViewById(R.id.list_order_row).setBackgroundResource(R.drawable.border_right_red);
            }

            ((TextView)row.findViewById(R.id.order)).setText(order.toString());

            String price = Float.toString(order.getPrice()) + " €";
            ((TextView)row.findViewById(R.id.price)).setText(price);

            ImageView icon = row.findViewById(R.id.icon);

            switch (order.getType()){
                case COFFEE:
                    icon.setImageResource(R.drawable.coffee);
                    break;
                case SODA:
                    icon.setImageResource(R.drawable.soda);
                    break;
                case SANDWICH:
                    icon.setImageResource(R.drawable.sandwich);
                    break;
                case POPCORN:
                    icon.setImageResource(R.drawable.popcorn);
                    break;
                case MIXED:
                    icon.setImageResource(R.drawable.mixed);
                    break;
            }

            return (row);
        }

        private void refreshOrders() {
            notifyDataSetChanged();
        }
    }
}
