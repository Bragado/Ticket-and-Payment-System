package cmov.miguellucas.com.customerapp.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.Models.Ticket;
import cmov.miguellucas.com.customerapp.R;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.MyViewHolder>  {

    ArrayList<Ticket> tickets;
    Context context;
    GenerateQRCodeForTicket generateQRCode;
    boolean displayQr;
    public TicketsAdapter(Context context, ArrayList<Ticket> tickets, boolean displayQr) {
        this.context = context;
        this.tickets = tickets;
        if(displayQr)
            generateQRCode = (GenerateQRCodeForTicket)context;
        this.displayQr = displayQr;
    }

    @Override
    public TicketsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_row, parent,false);


        return new TicketsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketsAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(tickets.get(position).eventTitle);
        holder.date.setText(tickets.get(position).date);
        if(displayQr)
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateQRCode.generateQR(tickets.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
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

    public interface GenerateQRCodeForTicket {
        public void generateQR(Ticket ticket);
    }

}
