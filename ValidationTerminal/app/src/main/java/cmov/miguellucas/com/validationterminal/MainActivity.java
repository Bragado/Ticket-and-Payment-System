package cmov.miguellucas.com.validationterminal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import Logic.Ticket;
import ServerRequests.ValidateTickets;

public class MainActivity extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private final static int SCAN_REQUEST_CODE = 0;
    public final static String ID_EXTRA = "org.proj1.validationTerminal.tickets";

    private ArrayList<Ticket> tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tickets = new ArrayList<>();
    }

    @Override
    protected void onResume(){
        super.onResume();
        tickets = new ArrayList<>();
    }

    public void btnValidateClick(View v){
        scanOrder();
    }

    public void scanOrder() {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, SCAN_REQUEST_CODE);
        }
        catch (ActivityNotFoundException anfe) {
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                act.startActivity(intent);
            }
        });
        downloadDialog.setNegativeButton(buttonNo, null);
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCAN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");

                String[] parameters = contents.split("&");
                int index = parameters[0].indexOf("=");
                String ticketId = parameters[0].substring(index+1);

                index = parameters[1].indexOf("=");
                String userId = parameters[1].substring(index+1);

                Ticket ticket = new Ticket(ticketId, userId);
                tickets.add(ticket);

                Toast.makeText(this,getString(R.string.tickets_validated), Toast.LENGTH_SHORT).show();
                validateTicketsRequest();
            }
        }
    }

    public void validateTicketsRequest(){
        int ticketsVerified = 0;

        for (Ticket ticket:tickets) {
            ValidateTickets validateTickets = new ValidateTickets(ticket);
            Thread thread = new Thread(validateTickets);
            thread.start();
            try {
                thread.join();
                ticketsVerified++;

                if (ticketsVerified == tickets.size()){
                    startResultActivity();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*Toast.makeText(this, getString(R.string.order_received), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, OrderListActivity.class);
            startActivity(i);*/

    }

    public void startResultActivity(){
        Intent i = new Intent(this, TicketValidationActivity.class);
        //boolean tickets[] = {true, true, false, true};
        i.putExtra(ID_EXTRA, tickets);
        startActivity(i);
    }
}
