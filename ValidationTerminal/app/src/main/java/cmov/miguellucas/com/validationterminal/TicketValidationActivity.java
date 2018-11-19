package cmov.miguellucas.com.validationterminal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Logic.Ticket;

public class TicketValidationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_validation);
        setTitle(R.string.validation_activity_title);

        ArrayList<Ticket> tickets = (ArrayList<Ticket>) getIntent().getSerializableExtra(MainActivity.ID_EXTRA);

        buildLayout(tickets);
    }

    private void buildLayout(ArrayList<Ticket> tickets){
        LinearLayout linearLayout = findViewById(R.id.tickets_list);
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < tickets.size(); i++) {
            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.ticket_row, null, false);

            //draw validated or invalidated ticket image and text
            TextView validText = row.findViewById(R.id.ticket_valid);
            ImageView image = row.findViewById(R.id.ticket_image);
            if (tickets.get(i).getStatus() == Ticket.VALID){
                validText.setText(R.string.ticket_valid);
                image.setImageResource(R.drawable.circle_green);
            } else if (tickets.get(i).getStatus() == Ticket.REJECTED) {
                validText.setText(R.string.ticket_invalid);
                image.setImageResource(R.drawable.circle_red);
            }

            //write ticket number
            TextView idText = row.findViewById(R.id.ticket_id);
            idText.setText(String.format(getString(R.string.ticket_number), (i+1)));

            linearLayout.addView(row, linearLayout.getChildCount() - 1);
        }

    }

    public void goBackClick(View v){
        finish();
    }
}
