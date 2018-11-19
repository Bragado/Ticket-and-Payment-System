package cmov.miguellucas.com.customerapp.Cafeteria;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cmov.miguellucas.com.customerapp.Database.TicketDbHelper;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.Utils.SharedPreferenceConfig;

public class CafeteriaFragment extends Fragment {

    private final int VOUCHER_NONE = -1;
    private final int VOUCHER_COFFEE = 1;
    private final int VOUCHER_POPCORN = 3;
    private final int VOUCHER_DISCOUNT = 0;

    private int voucher1 = VOUCHER_NONE;
    private int voucher2 = VOUCHER_NONE;
    private int nCoffees = 0;
    private int nPopcorns = 0;
    private int nSodas = 0;
    private int nSandwichs = 0;

    GenerateQrCode qrCode;

    TextView coffeeNumberText;
    TextView popcornNumberText;
    TextView discountNumberText;

    public CafeteriaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cafeteria, container, false);
        qrCode = (GenerateQrCode) getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //TODO
        //show number of available vouchers
        //show price of products
        coffeeNumberText = getView().findViewById(R.id.voucher_coffee_number);
        coffeeNumberText.setText(String.format(getString(R.string.voucher_number), 3));
        popcornNumberText = getView().findViewById(R.id.voucher_popcorn_number);
        popcornNumberText.setText(String.format(getString(R.string.voucher_number), 1));
        discountNumberText = getView().findViewById(R.id.voucher_discount_number);
        discountNumberText.setText(String.format(getString(R.string.voucher_number), 1));

        buildLayout();
        updateVouchersValue();
    }
    public void updateVouchersValue() {
        TicketDbHelper contactDbHelper = new TicketDbHelper(getContext());
        SQLiteDatabase db = contactDbHelper.getReadableDatabase();
        Log.e("coffe vouchers: " ,""+ contactDbHelper.countVouchers(db,1));

        coffeeNumberText.setText(String.format(getString(R.string.voucher_number), contactDbHelper.countVouchers(db,1)));
        popcornNumberText.setText(String.format(getString(R.string.voucher_number), contactDbHelper.countVouchers(db,3)));
        discountNumberText.setText(String.format(getString(R.string.voucher_number), contactDbHelper.countVouchers(db,0)));
    }



    public void buildLayout(){

        LinearLayout coffeeLayout = getView().findViewById(R.id.layout_coffee);
        LinearLayout popcornLayout = getView().findViewById(R.id.layout_popcorn);
        LinearLayout discountLayout = getView().findViewById(R.id.layout_discount);
        Button btnCreateOrder = getView().findViewById(R.id.btn_create_order);
        Button btnAddCoffee = getView().findViewById(R.id.btn_add_coffee);
        Button btnAddSoda = getView().findViewById(R.id.btn_add_soda);
        Button btnAddSandwich = getView().findViewById(R.id.btn_add_sandwich);
        Button btnAddPopcorn = getView().findViewById(R.id.btn_add_popcorn);

        //set click listeners to voucher layouts and buttons
        coffeeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVoucher(VOUCHER_COFFEE);
            }
        });

        popcornLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVoucher(VOUCHER_POPCORN);
            }
        });

        discountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVoucher(VOUCHER_DISCOUNT);
            }
        });

        btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateOrder();
            }
        });

        btnAddCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCoffee();
            }
        });

        btnAddPopcorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopcorn();
            }
        });

        btnAddSandwich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSandwich();
            }
        });

        btnAddSoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSoda();
            }
        });
    }

    public void selectVoucher(int voucher){
        //in case voucher is already selected -> deselect it
        if (voucher1 == voucher){
            voucher1 = voucher2;
            voucher2 = VOUCHER_NONE;
        } else if (voucher2 == voucher){
            voucher2 = VOUCHER_NONE;
        } else {
            //if its not selected -> select it
            if (voucher1 == VOUCHER_NONE){
                voucher1 = voucher;
            } else if (voucher2 == VOUCHER_NONE){
                voucher2 = voucher;
            } else {
                voucher1 = voucher2;
                voucher2 = voucher;
            }
        }

        toggleSelectedImgs();
    }

    public void toggleSelectedImgs(){
        ImageView coffeeImg = getView().findViewById(R.id.img_coffee_selected);
        ImageView popcornImg = getView().findViewById(R.id.img_popcorn_selected);
        ImageView discountImg = getView().findViewById(R.id.img_discount_selected);
        View coffeeActive = getView().findViewById(R.id.voucher_coffee_active);
        View popcornActive = getView().findViewById(R.id.voucher_popcorn_active);
        View discountActive = getView().findViewById(R.id.voucher_discount_active);

        if (voucher1 == VOUCHER_COFFEE || voucher2 == VOUCHER_COFFEE){
            coffeeImg.setVisibility(View.VISIBLE);
            coffeeActive.setVisibility(View.VISIBLE);
        } else {
            coffeeImg.setVisibility(View.INVISIBLE);
            coffeeActive.setVisibility(View.INVISIBLE);
        }

        if (voucher1 == VOUCHER_POPCORN || voucher2 == VOUCHER_POPCORN){
            popcornImg.setVisibility(View.VISIBLE);
            popcornActive.setVisibility(View.VISIBLE);
        } else {
            popcornImg.setVisibility(View.INVISIBLE);
            popcornActive.setVisibility(View.INVISIBLE);
        }

        if (voucher1 == VOUCHER_DISCOUNT || voucher2 == VOUCHER_DISCOUNT){
            discountImg.setVisibility(View.VISIBLE);
            discountActive.setVisibility(View.VISIBLE);
        } else {
            discountImg.setVisibility(View.INVISIBLE);
            discountActive.setVisibility(View.INVISIBLE);
        }
    }

    public void generateOrder(){
        try {
            JSONObject jsonObject = generateJSON();
            qrCode.generate(jsonObject);
            Log.e("CAFETERIA JSON", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject generateJSON() throws JSONException {

        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(getContext());
        String userID = sharedPreferenceConfig.readUUID();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userID", userID);
        jsonObject.put("signedUserID", "signedUserID");

        JSONArray products = new JSONArray(); // {"type" :1, "quantity": 3}
        JSONObject obj = new JSONObject();
        obj.put("type", 1);
        obj.put("quantity", nCoffees);
        products.put(obj);
        obj = new JSONObject();
        obj.put("type", 2);
        obj.put("quantity", nSodas);
        products.put(obj);
        obj = new JSONObject();
        obj.put("type", 3);
        obj.put("quantity", nPopcorns);
        products.put(obj);
        obj = new JSONObject();
        obj.put("type", 4);
        obj.put("quantity", nSandwichs);
        products.put(obj);
        jsonObject.put("products", products);

        TicketDbHelper contactDbHelper = new TicketDbHelper(getContext());
        SQLiteDatabase db = contactDbHelper.getReadableDatabase();

        JSONArray vouchers = new JSONArray();

        String vId = contactDbHelper.getVoucher(db, voucher1);
        if(!vId.equals(""))
            vouchers.put(vId);

        vId = contactDbHelper.getVoucher(db, voucher2);
        if(!vId.equals(""))
            vouchers.put(vId);
        jsonObject.put("vouchers", vouchers);

        return jsonObject;
    }

    public void addCoffee(){
        nCoffees++;
        TextView nCoffeeText = getView().findViewById(R.id.order_coffee_number);
        nCoffeeText.setText(String.format(getString(R.string.voucher_number), nCoffees));
    }

    public void addSoda(){
        nSodas++;
        TextView nSodaText = getView().findViewById(R.id.order_soda_number);
        nSodaText.setText(String.format(getString(R.string.voucher_number), nSodas));
    }

    public void addSandwich(){
        nSandwichs++;
        TextView nSandwichText = getView().findViewById(R.id.order_sandwich_number);
        nSandwichText.setText(String.format(getString(R.string.voucher_number), nSandwichs));
    }

    public void addPopcorn(){
        nPopcorns++;
        TextView nPopcornText = getView().findViewById(R.id.order_popcorn_number);
        nPopcornText.setText(String.format(getString(R.string.voucher_number), nPopcorns));
    }

    public interface GenerateQrCode {
        public void generate(JSONObject jsonObject);
    }

}