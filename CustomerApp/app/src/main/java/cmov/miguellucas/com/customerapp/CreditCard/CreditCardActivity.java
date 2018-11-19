package cmov.miguellucas.com.customerapp.CreditCard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;

import java.util.Date;

import cmov.miguellucas.com.customerapp.Home.HomeFragment;
import cmov.miguellucas.com.customerapp.Home.MainActivity;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.ServerOperations.AddCreditCard;
import cmov.miguellucas.com.customerapp.Utils.SharedPreferenceConfig;

public class CreditCardActivity extends AppCompatActivity implements AddCreditCard.CreditCardOP {

    CardForm cardForm;
    Button add;
    Context context;
    Activity activity;
    ProgressBar progressBar;
    public String address = "http://10.0.2.2:7074/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        context = this;
        activity = this;
        cardForm = findViewById(R.id.card_form);
        add = findViewById(R.id.btnBuy);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .setup(CreditCardActivity.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    String cardN =  "" + cardForm.getCardNumber();
                    String date =  new Date().toString();

                    int CCC =  Integer.parseInt(cardForm.getCvv()) ;
                    SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(context);
                    String uuid = sharedPreferenceConfig.readUUID();
                    AddCreditCard addCreditCard = new AddCreditCard(context, cardN, date, CCC, address, uuid);
                    Thread thread = new Thread(addCreditCard);
                    thread.start();
                    progressBar.setVisibility(View.VISIBLE);

                }else {
                    Toast.makeText(CreditCardActivity.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void done() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(context);
                sharedPreferenceConfig.writeCreditCardStatus(true);
                startActivity(new Intent(activity, MainActivity.class));
                finish();
                return;
            }
        });
    }
}
