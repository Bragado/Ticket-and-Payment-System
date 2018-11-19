package cmov.miguellucas.com.customerapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

import cmov.miguellucas.com.customerapp.R;

public class SharedPreferenceConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context context){
        this.context = context;

        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preference), Context.MODE_PRIVATE);

    }

      public void writeLoginStatus(boolean value, String UUID)
      {
          SharedPreferences.Editor editor = sharedPreferences.edit();
          editor.putBoolean(context.getResources().getString(R.string.login_status_preferences), value);
          editor.putString(context.getResources().getString(R.string.login_private_UUID_preferences), UUID);
          editor.commit();
      }

      public void savePrivateKey(PrivateKey key) {
          SharedPreferences.Editor editor = sharedPreferences.edit();
          editor.putString(context.getResources().getString(R.string.login_private_key_preferences),   Base64.encodeToString(key.getEncoded(), Base64.DEFAULT));
          editor.commit();
      }

      public PrivateKey getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
            String pv = sharedPreferences.getString(context.getResources().getString(R.string.login_private_key_preferences), "");
            byte[] pvt =  Base64.decode(pv, Base64.DEFAULT);
            PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(pvt);
          KeyFactory kf = KeyFactory.getInstance("RSA");
          return kf.generatePrivate(ks);
    }

    public void writeCreditCardStatus(boolean value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.credit_card_preference), value);
        editor.commit();
    }


    public boolean readLogin()
      {
            boolean status = false;

            status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preferences), false);

            return status;
      }

      public boolean readCreditCard()
      {
          boolean status = false;

          status = sharedPreferences.getBoolean(context.getResources().getString(R.string.credit_card_preference), false);

          return status;
      }

      public String readUUID() {

        return sharedPreferences.getString(context.getResources().getString(R.string.login_private_UUID_preferences), "");
      }

        public String readPrivateKey() {

        return sharedPreferences.getString(context.getResources().getString(R.string.login_private_key_preferences), "");
    }



}
