package cmov.miguellucas.com.customerapp.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.Models.Order;
import cmov.miguellucas.com.customerapp.Models.Ticket;
import cmov.miguellucas.com.customerapp.R;

public class OrdersAdapter  extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder>  {

    ArrayList<Order> orders;
    Context context;
    OpenOrder openOrder;

    public OrdersAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
        openOrder = (OpenOrder) context;

    }

    @Override
    public OrdersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_row, parent,false);


        return new OrdersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrdersAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(orders.get(position).date);
        holder.date.setText("" + orders.get(position).price + "€");

            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openOrder.open(orders.get(position));
                }
            });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
        }
    }

    public interface OpenOrder {
        public void open(Order order);
    }
}
