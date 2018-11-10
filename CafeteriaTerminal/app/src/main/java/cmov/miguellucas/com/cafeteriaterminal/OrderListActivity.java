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
import android.widget.Toast;

import logic.Cafeteria;
import logic.Order;

public class OrderListActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    public final static String ID_EXTRA = "org.proj1.cafeteriaTerminal.order";
    private Cafeteria cafeteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        cafeteria = (Cafeteria) getIntent().getSerializableExtra(MainActivity.ID_EXTRA);

        ListView list = findViewById(R.id.order_list);
        OrderAdapter orderAdapter = new OrderAdapter();

        list.setAdapter(orderAdapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        //Toast.makeText(this, "dsfaf", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, OrderActivity.class);
        i.putExtra(ID_EXTRA, cafeteria.getOrders().get(pos));
        startActivityForResult(i);
    }

    class OrderAdapter extends ArrayAdapter<Order> {
        OrderAdapter() {
            super(OrderListActivity.this, R.layout.order_row, cafeteria.getOrders());
        }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.order_row, parent, false);    // get our custom layout
            }
            Order order = cafeteria.getOrders().get(position);

            if (order.getOrderServed())
                row.findViewById(R.id.order_row).setBackgroundResource(R.drawable.border_right_green);
            else
                row.findViewById(R.id.order_row).setBackgroundResource(R.drawable.border_right_red);

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
    }
}
