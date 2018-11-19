package cmov.miguellucas.com.customerapp.Register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import cmov.miguellucas.com.customerapp.Home.MainActivity;
import cmov.miguellucas.com.customerapp.R;
import cmov.miguellucas.com.customerapp.ServerOperations.Register;
import cmov.miguellucas.com.customerapp.Utils.ServerOps;
import cmov.miguellucas.com.customerapp.Utils.SharedPreferenceConfig;

public class RegisterActivity extends AppCompatActivity implements Register.RegisterOP {

    private static final String TAG = "RegisterActivity";
    private Context context;
    private ProgressBar progressBar;
    private EditText name, username, password, nif;
    private TextView wait ;
    public String address = "http://10.0.2.2:7074/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        wait = (TextView)findViewById(R.id.wait_text);
        name = (EditText)findViewById(R.id.input_name);
        username= (EditText)findViewById(R.id.input_username);
        password= (EditText)findViewById(R.id.input_password);
        nif= (EditText)findViewById(R.id.input_nif);
        context = this;


        progressBar.setVisibility(View.GONE);
        wait.setVisibility(View.GONE);

         init();

        Log.d(TAG, "onCreate: started.");
        address = getResources().getString(R.string.address);
    }

    private void init() {
        Button register = (Button) findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String Username = username.getText().toString();
                String Password = password.getText().toString();
                String Nif = nif.getText().toString();

                if(ServerOps.isStringNull(Name) || ServerOps.isStringNull(Username) || ServerOps.isStringNull(Password) || ServerOps.isStringNull(Nif)) {
                    Toast.makeText(context, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    int NIF = Integer.parseInt(Nif);
                    KeyPair keyPair = null;
                    PrivateKey privateKey = null;
                    PublicKey publicKey = null;
                    try {
                        keyPair = ServerOps.GenerateKeys();
                        privateKey = (PrivateKey) ServerOps.getPrivateKey(keyPair);
                        publicKey = ServerOps.getPublicKey(keyPair);
                        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(context);
                        sharedPreferenceConfig.savePrivateKey(privateKey);

                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    Log.e("REGITERACTIVITY", publicKey.toString());
                    progressBar.setVisibility(View.VISIBLE);
                    wait.setVisibility(View.VISIBLE);
                    Register register1 = new Register(context, Name, Username, Password, NIF, Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT), address );
                    Thread thread = new Thread(register1);
                    thread.start();
                }



            }
        });
    }



    @Override
    public void done(final String uuid) {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(uuid.equals("")) {
                    Toast.makeText(context, "Something went wrong, please retry later", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    wait.setVisibility(View.GONE);
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(uuid);
                        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(context);
                        sharedPreferenceConfig.writeLoginStatus(true, jsonObject.getString("userID"));
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Something went wrong, please retry later", Toast.LENGTH_SHORT).show();
                    }




                }

            }
        });


    }
}
