package cmov.miguellucas.com.customerapp.Utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.Models.Event;
import cmov.miguellucas.com.customerapp.R;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder>  {

    public ArrayList<Event> events ;
    Context context;
    SeeTicket seeTicket ;

    public PostsAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
        seeTicket = (SeeTicket)context;
    }

    public void updateEvents(ArrayList events) {
        this.events = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.e("PostsAdaptar" , "Fui chamado!!!");

        holder.title.setText(events.get(position).title);
        holder.date.setText(events.get(position).date);
        Picasso.get().load(events.get(position).photoName).into(holder.photo);

        holder.photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                seeTicket.openTicket(events.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        if(events != null)
            return events.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;
        ImageView photo;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            photo = itemView.findViewById(R.id.photo);
        }
    }



    public interface SeeTicket {
        public void openTicket(Event e);

    }


}
