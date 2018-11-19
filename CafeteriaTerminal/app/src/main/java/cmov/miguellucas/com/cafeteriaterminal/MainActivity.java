package cmov.miguellucas.com.cafeteriaterminal;

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

import ServerRequests.CreateOrder;
import ServerRequests.ServerUtils;
import logic.Cafeteria;
import logic.Order;
import logic.Voucher;

public class MainActivity extends AppCompatActivity {
    public final static String ID_EXTRA = "org.proj1.cafeteriaTerminal.orderList";
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private final static int SCAN_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for testing purposes
        dummyData();
    }

    public void btnReadOrderClick(View v) {
        scanOrder();
    }

    public void btnOrderListClick(View v) {
        Intent i = new Intent(this, OrderListActivity.class);
        startActivity(i);
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
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");

                Order order = ServerUtils.parseOrderFromQR(contents);
                sendOrderRequest(order);
                Toast.makeText(this,"Format: " + format + "\nMessage: " + contents, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendOrderRequest(Order order){
        Log.d("order", order.toJson().toString());
        CreateOrder createOrder = new CreateOrder(order);
        Thread thread = new Thread(createOrder);
        thread.start();
        try {
            thread.join();


            Toast.makeText(this, getString(R.string.order_received), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, OrderListActivity.class);
            startActivity(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void dummyData(){
        /*Order order1 = new Order(1,1);
        order1.addProduct(Cafeteria.getInstance().getProducts().get(0));
        Order order2 = new Order(2,2);
        order2.addProduct(Cafeteria.getInstance().getProducts().get(1));
        order2.addProduct(Cafeteria.getInstance().getProducts().get(1));
        order2.setOrderServed(true);
        Order order3 = new Order(3,2);
        order3.addProduct(Cafeteria.getInstance().getProducts().get(0));
        order3.addProduct(Cafeteria.getInstance().getProducts().get(1));
        order3.addProduct(Cafeteria.getInstance().getProducts().get(2));
        order3.addProduct(Cafeteria.getInstance().getProducts().get(2));
        order3.addProduct(Cafeteria.getInstance().getProducts().get(3));
        Order order4 = new Order(4,1);
        order4.addProduct(Cafeteria.getInstance().getProducts().get(3));
        Order order5 = new Order(5,1);
        order5.addProduct(Cafeteria.getInstance().getProducts().get(3));
        order5.addProduct(Cafeteria.getInstance().getProducts().get(2));
        Order order6 = new Order(6,1);
        order6.addProduct(Cafeteria.getInstance().getProducts().get(2));

        /*Voucher voucher1 = new Voucher(1, 2);
        Voucher voucher2 = new Voucher(2, 15);
        Voucher voucher3 = new Voucher(3, 10);*/

        /*order1.setVoucher1(voucher1);
        order1.setVoucher2(voucher2);
        order3.setVoucher1(voucher3);

        Cafeteria.getInstance().addOrder(order1);
        Cafeteria.getInstance().addOrder(order2);
        Cafeteria.getInstance().addOrder(order3);
        Cafeteria.getInstance().addOrder(order4);
        Cafeteria.getInstance().addOrder(order5);
        Cafeteria.getInstance().addOrder(order6);*/
    }
}
