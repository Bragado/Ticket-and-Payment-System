package cmov.miguellucas.com.cafeteriaterminal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import logic.Cafeteria;
import logic.Order;

public class OrderListActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    public final static String ID_EXTRA = "org.proj1.cafeteriaTerminal.order";
    private final static int ORDER_REQUEST_CODE = 0;
    private OrderAdapter orderAdapter;
    private String DEBUG_TAG = "CMOV_MIGUEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        ListView list = findViewById(R.id.order_list);
        orderAdapter = new OrderAdapter();

        list.setAdapter(orderAdapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Intent i = new Intent(this, OrderActivity.class);
        i.putExtra(ID_EXTRA, Cafeteria.getInstance().getOrders().get(pos));
        startActivityForResult(i, ORDER_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ORDER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Order order = (Order) data.getSerializableExtra(OrderListActivity.ID_EXTRA);
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
            //this.clear();
            //this.addAll(Cafeteria.getInstance().getOrders());
            notifyDataSetChanged();
        }
    }
}
