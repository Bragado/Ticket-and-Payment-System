package cmov.miguellucas.com.cafeteriaterminal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import logic.Cafeteria;
import logic.Order;

public class MainActivity extends AppCompatActivity {
    public final static String ID_EXTRA = "org.proj1.cafeteriaTerminal.orderList";
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private Cafeteria cafeteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cafeteria = new Cafeteria();
        cafeteria.startCafeteria();

        //for testing purposes
        dummyData();
    }

    public void btnReadOrderClick(View v) {
        scanOrder();
        //Toast.makeText(this,"Clicked read order",Toast.LENGTH_SHORT).show();
    }

    public void btnOrderListClick(View v) {
        Intent i = new Intent(this, OrderListActivity.class);
        i.putExtra(ID_EXTRA, cafeteria);
        startActivity(i);
    }

    public void scanOrder() {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
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
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");

                //TODO: parse data read from qr
                Toast.makeText(this,"Format: " + format + "\nMessage: " + contents, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dummyData(){
        Order order1 = new Order(1);
        order1.addProduct(cafeteria.getProducts().get(0));
        Order order2 = new Order(2);
        order2.addProduct(cafeteria.getProducts().get(1));
        order2.addProduct(cafeteria.getProducts().get(1));
        order2.setOrderServed(true);
        Order order3 = new Order(2);
        order3.addProduct(cafeteria.getProducts().get(0));
        order3.addProduct(cafeteria.getProducts().get(1));
        order3.addProduct(cafeteria.getProducts().get(2));
        order3.addProduct(cafeteria.getProducts().get(2));
        order3.addProduct(cafeteria.getProducts().get(3));
        Order order4 = new Order(1);
        order4.addProduct(cafeteria.getProducts().get(3));
        Order order5 = new Order(1);
        order5.addProduct(cafeteria.getProducts().get(3));
        order5.addProduct(cafeteria.getProducts().get(2));
        Order order6 = new Order(1);
        order6.addProduct(cafeteria.getProducts().get(2));

        cafeteria.addOrder(order1);
        cafeteria.addOrder(order2);
        cafeteria.addOrder(order3);
        cafeteria.addOrder(order4);
        cafeteria.addOrder(order5);
        cafeteria.addOrder(order6);
    }
}
