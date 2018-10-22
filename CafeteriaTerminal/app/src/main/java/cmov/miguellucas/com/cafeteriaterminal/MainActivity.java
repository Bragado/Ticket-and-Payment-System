package cmov.miguellucas.com.cafeteriaterminal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import logic.Cafeteria;
import logic.Order;

public class MainActivity extends AppCompatActivity {
    public final static String ID_EXTRA = "org.proj1.cafeteriaTerminal.orderList";
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
        Toast.makeText(this,"Clicked read order",Toast.LENGTH_SHORT).show();
    }

    public void btnOrderListClick(View v) {
        Intent i = new Intent(this, OrderListActivity.class);
        i.putExtra(ID_EXTRA, cafeteria);
        startActivity(i);
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
